document.addEventListener('DOMContentLoaded', function () {
    updateUIBasedOnLogin();
});

document.getElementById('logout-btn').addEventListener('click', function (event) {
    event.preventDefault();
    fetch('/logout', {
        method: 'POST',
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                console.error('Logout failed');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

function updateUIBasedOnLogin() {
    fetch('/login/check')
        .then(response => {
            if (!response.ok) {
                throw new Error('Not logged in or other error');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('profile-name').textContent = data.name; // 프로필 이름 설정
            document.querySelector('.nav-item.dropdown').style.display = 'block'; // 드롭다운 메뉴 표시
            document.querySelector('.nav-item a[href="/login"]').parentElement.style.display = 'none'; // 로그인 버튼 숨김
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('profile-name').textContent = 'Profile'; // 기본 텍스트로 재설정
            document.querySelector('.nav-item.dropdown').style.display = 'none'; // 드롭다운 메뉴 숨김
            document.querySelector('.nav-item a[href="/login"]').parentElement.style.display = 'block'; // 로그인 버튼 표시
        });
}

document.getElementById("navbarDropdown").addEventListener('click', function (e) {
    e.preventDefault();
    const dropdownMenu = e.target.closest('.nav-item.dropdown').querySelector('.dropdown-menu');
    dropdownMenu.classList.toggle('show');
});


function login() {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (!email || !password) {
        alert('Please fill in all fields.');
        return;
    }

    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
        .then(response => {
            if (200 === !response.status) {
                alert('Login failed');
                throw new Error('Login failed');
            }
        })
        .then(() => {
            updateUIBasedOnLogin();
            window.location.href = '/';
        })
        .catch(error => {
            console.error('Error during login:', error);
        });
}

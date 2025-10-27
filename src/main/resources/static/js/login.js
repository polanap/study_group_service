const originalFetch = window.fetch;

window.fetch = function(...args) {
    let [url, config = {}] = args;
    const token = localStorage.getItem("token");

    // Проверяем, существует ли токен и является ли запрос к API
    // (по желанию, можно добавить проверку, чтобы не отправлять токен
    // на сторонние ресурсы).
    if (token) {
        config.headers = {
            ...config.headers,
            'Authorization': `Bearer ${token}`
        };
    }

    return originalFetch(url, config);
};

document.getElementById('registrationForm').onsubmit = async function(event) {
    event.preventDefault(); // Предотвращаем стандартное поведение формы

    const formData = new FormData(this);
    const data = {
        username: formData.get('username'),
        password: formData.get('password'),
        confirmPassword: formData.get('confirmPassword')
    };

    try {
        const response = await fetch('/api/registration', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const token = await response.text(); // Получаем токен
            localStorage.setItem("token", token); // Сохраняем токен в локальном хранилище
            window.location.href = '/people'; // Перенаправляем
        } else {
            const errorText = await response.text();
            const error = JSON.parse(errorText).errorMessage
            document.getElementById('errorMessage').textContent = error;
            document.getElementById('errorMessage').style.display = 'block';
        }
    } catch (error) {
        console.error('Ошибка:', error);
        document.getElementById('errorMessage').textContent = 'Ошибка при регистрации. Попробуйте еще раз.';
        document.getElementById('errorMessage').style.display = 'block';
    }
};
document.addEventListener('DOMContentLoaded', function () {

    const toggles = document.querySelectorAll('[data-toggle-password]');

    toggles.forEach(button => {
        button.addEventListener('click', function () {
            const targetId = this.getAttribute('data-toggle-password');
            const passwordInput = document.getElementById(targetId);
            const eyeIcon = this.querySelector('i');

            if (!passwordInput || !eyeIcon) return;

            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);

            eyeIcon.classList.toggle('bi-eye');
            eyeIcon.classList.toggle('bi-eye-slash');
        });
    });
});
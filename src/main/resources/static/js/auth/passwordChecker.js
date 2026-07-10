document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');
    const password = document.getElementById('loginPassword');
    const confirmPassword = document.getElementById('confirmPassword');
    const differentPassword = document.getElementById('differentPasswords');
    const error = document.getElementById('wrongPassword');

    form.addEventListener('submit', function (event) {
        let hasError = false;

        // checks that passwords are the same
        if (password.value !== confirmPassword.value) {
            differentPassword.classList.remove('d-none');
            hasError = true;
        } else {
            differentPassword.classList.add('d-none');
        }

        // checks password length and id_20
        if (password.value.length != 8 || !password.value.includes("id_20")) {
            console.log("error");g
            error.classList.remove('d-none');
            hasError = true;
        } else {
            error.classList.add('d-none');
        }

        if (hasError) {
            event.preventDefault();
            password.classList.add('is-invalid');
            confirmPassword.classList.add('is-invalid');
        } else {
            password.classList.remove('is-invalid');
            confirmPassword.classList.remove('is-invalid');
        }
    });

    // removes errors when user is fixing
    [password, confirmPassword].forEach(input => {
        input.addEventListener('input', function() {
            password.classList.remove('is-invalid');
            confirmPassword.classList.remove('is-invalid');
            differentPassword.classList.add('d-none');
            error.classList.add('d-none');
        });
    });
});
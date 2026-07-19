document.addEventListener('DOMContentLoaded', () => {
    const resetButton = document.getElementById('resetButton');
    const form = document.querySelector('form');

    if (resetButton && form) {
        resetButton.addEventListener('click', (e) => {
            if (confirm("Sei sicuro di voler cancellare tutti i dati inseriti?")) {
                form.reset();
                const alerts = document.querySelectorAll('.alert');
                alerts.forEach(alert => alert.classList.add('d-none'));
            }
        });
    }
});
function inizializzaCarosello() {
    const carouselEl = document.getElementById('carouselExample');
    if (carouselEl) {
        // destroying the old instance to avoid duplicates
        const existing = bootstrap.Carousel.getInstance(carouselEl);
        if (existing) existing.dispose();

        new bootstrap.Carousel(carouselEl, {
            interval: 3000,
            ride: 'carousel',
            pause: 'hover'
        });
    }
}

document.addEventListener('DOMContentLoaded', () => {
    fetch('/dashboard/recensioni/api/carosello')
        .then(res => res.text())
        .then(html => {
            const wrapper = document.getElementById('carouselWrapper');
            if (wrapper) {
                wrapper.innerHTML = html;
                inizializzaCarosello();
            }
        });
});

function inviaRecensione(testo) {
    // Retrieve csrf meta tag for authentication
    const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/dashboard/recensioni/api/invia', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token // here we insert the csrf token
        },
        body: JSON.stringify({ testo: testo })
    })
        .then(res => {
            if (res.ok) {
                // if send was successful we restart the carousel
                fetch('/dashboard/recensioni/api/carosello')
                    .then(r => r.text())
                    .then(html => {
                        document.getElementById('carouselWrapper').innerHTML = html;
                        // Re-inizializziamo il carosello
                        inizializzaCarosello();
                    });
            } else {
                alert("Errore nell'invio della recensione.");
            }
        })
        .catch(err => console.error("Errore:", err));
}
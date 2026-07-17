function avviaCarosello() {
    const el = document.querySelector('#carouselExample');
    if (el && typeof bootstrap !== 'undefined') {
        new bootstrap.Carousel(el, {
            interval: 5000,
            wrap: true,
            ride: 'carousel'
        });
    }
}
function avviaCarosello() {
    const el = document.querySelector('#carouselExample');
    if (el && typeof bootstrap !== 'undefined') {
        new bootstrap.Carousel(el, {
            interval: 30000, // 30 seconds as requested
            wrap: true,
            ride: 'carousel'
        });
    }
}
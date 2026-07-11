/* Script that make the carousel animate */
document.addEventListener("DOMContentLoaded", function () {
    const recensioniCarouselEl = document.querySelector('#carouselExample');

    if (recensioniCarouselEl) {
        // carousel implemented via bootstrap
        const carousel = new bootstrap.Carousel(recensioniCarouselEl, {
            interval: 5000,    // 5s rotation time
            wrap: true,        // Enables circular rotation
            ride: 'carousel'   // Start the animation at loading time
        });
    }
});
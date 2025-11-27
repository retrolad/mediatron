(function () {
    // Подсветка активной ссылки по текущему пути
    const links = document.querySelectorAll('.sidebar-nav .nav-link');
    const path = window.location.pathname;
    links.forEach(a => {
        if (path.startsWith(a.getAttribute('href'))) {
            a.classList.add('active');
        }
    });
})();


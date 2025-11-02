document.body.addEventListener('htmx:beforeSwap', function(e) {
    const target = e.detail.target;
    if (!target || target.id !== 'movies-list') return;

    // Отключаем автоматическую замену, чтобы сначала выполнить анимацию
    e.detail.shouldSwap = false;

    const duration = 250; // должен соответствовать CSS transition
    target.classList.add('fade-out');

    setTimeout(function() {
        // Серверный ответ, который собирался подставить htmx
        const resp = e.detail.serverResponse || '';
        target.innerHTML = resp;

        // Анимация появления
        target.classList.remove('fade-out');
        target.classList.add('fade-in');

        setTimeout(function() {
            target.classList.remove('fade-in');
        }, duration);
    }, duration);
});
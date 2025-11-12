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

        // Сбрасываем прокрутку мгновенно — чтобы список всегда показывался сверху
        try {
            target.scrollTop = 0;
        } catch (err) {
            // noop
        }

        // При подгрузке картинок держим скролл вверху (чтобы картинки не "сдвигали" позицию)
        (function keepTopWhileImagesLoad(container) {
            if (!container) return;
            const imgs = Array.from(container.querySelectorAll('img'));
            if (!imgs.length) return;
            let remaining = imgs.length;

            function checkAndForceTop(img) {
                // если уже загружена — учесть как загруженную
                if (img.complete) {
                    remaining--;
                    if (remaining <= 0) {
                        // все загружены — ничего не делаем
                    }
                    return;
                }
                const onLoad = () => {
                    remaining--;
                    try { container.scrollTop = 0; } catch (e) {}
                    img.removeEventListener('load', onLoad);
                    img.removeEventListener('error', onLoad);
                };
                img.addEventListener('load', onLoad);
                img.addEventListener('error', onLoad);
            }

            imgs.forEach(checkAndForceTop);

            // safety: через 2s убираем слушатели и ставим финальный top=0
            setTimeout(() => {
                try { container.scrollTop = 0; } catch (e) {}
            }, 2000);
        })(target);

        // Анимация появления
        target.classList.remove('fade-out');
        target.classList.add('fade-in');

        setTimeout(function() {
            target.classList.remove('fade-in');
        }, duration);
    }, duration);
});
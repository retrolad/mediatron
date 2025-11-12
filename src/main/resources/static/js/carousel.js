const carousel = document.getElementById('carousel');
let sections = [];
let years = [];
let index = 0;
let isScrolling = false;
let isPointerOver = false; // <-- флаг наведения курсора

function updateLists() {
    sections = Array.from(document.querySelectorAll('.year-section'));
    years = Array.from(document.querySelectorAll('.year'));
    // повесить клики, если нужно
    years.forEach((y, idx) => {
        y.removeEventListener('click', y._clickHandler);
        // при клике переводим фокус на карусель и показываем секцию
        y._clickHandler = () => {
            if (carousel && typeof carousel.focus === 'function') carousel.focus();
            showSection(idx, true);
        };
        y.addEventListener('click', y._clickHandler);
    });
}

function getCurrentTranslateY(el) {
    const st = window.getComputedStyle(el);
    const tr = st.transform || st.webkitTransform;
    if (!tr || tr === 'none') return 0;
    const match = tr.match(/matrix.*\((.+)\)/);
    if (!match) return 0;
    const values = match[1].split(',').map(v => parseFloat(v));
    // matrix(a, b, c, d, tx, ty) или matrix3d(..., tx, ty, tz)
    return values.length === 6 ? values[5] : values[13] || 0;
}

function applyNeighborClasses() {
    years.forEach((y, idx) => {
        y.classList.remove('prev', 'next', 'active', 'prev-prev', 'next-next');
        if (index === idx) y.classList.add('active');
        else if (index === idx - 1) y.classList.add('prev');
        else if (index === idx + 1) y.classList.add('next');
        else if (index < idx) y.classList.add('prev-prev');
        else if (index > idx) y.classList.add('next-next');
    })
}

function showSection(i, skipClickDispatch = false) {
    updateLists();
    if (i < 0 || i >= sections.length) return;
    if (isScrolling) return;
    isScrolling = true;
    index = i;

    const target = sections[i];
    const sectionRect = target.getBoundingClientRect();
    const offset = (window.innerHeight - sectionRect.height) / 2;
    const currentTranslate = getCurrentTranslateY(carousel);
    // хотим, чтобы верх target оказался на позиции `offset` вьюпорта
    const delta = offset - sectionRect.top;
    const desiredTranslate = currentTranslate + delta;

    carousel.style.transition = 'transform 0.35s cubic-bezier(0.65, 0, 0.35, 1)';
    carousel.style.transform = `translateY(${desiredTranslate}px)`;

    applyNeighborClasses();

    if(!skipClickDispatch && years[i]) {
        years[i].dispatchEvent(new Event('click'));
    }
    setTimeout(() => isScrolling = false, 450);
}

// инициализация, ждем загрузки HTMX
document.addEventListener("htmx:load", () => {
    updateLists();
    showSection(index);
});

// отслеживаем наведение на карусель (pointer — покрывает мышь и некоторые тач-поведения)
if (carousel) {
    carousel.addEventListener('pointerenter', () => {
        isPointerOver = true;
    });
    carousel.addEventListener('pointerleave', () => {
        isPointerOver = false;
    });
}

// колесо — слушаем на элементе карусели, применяем когда карусель в фокусе или над ней курсор
if (carousel) {
    // Важно: passive=false, чтобы e.preventDefault() сработал при необходимости
    carousel.addEventListener('wheel', (e) => {
         // прокрутка работает только когда карусель в фокусе или над ней курсор
         if (isScrolling) return;
         if (document.activeElement !== carousel && !isPointerOver) return;

         // Предотвращаем прокрутку страницы ТОЛЬКО если карусель в фокусе.
         // Если пользователь просто наводит мышь, разрешаем странице скроллиться,
         // но при этом показываем смену секции карусели (без блокировки страницы).
         if (document.activeElement === carousel) {
             e.preventDefault();
         }

         if (e.deltaY > 0) showSection(index + 1);
         else if (e.deltaY < 0) showSection(index - 1);
    }, false);
}

// клавиши — обрабатываем только когда карусель в фокусе (keydown на элементе)
if (carousel) {
    carousel.addEventListener('keydown', (e) => {
        if (isScrolling) return;
        if (e.key === 'ArrowDown') {
            e.preventDefault();
            showSection(index + 1);
        }
        if (e.key === 'ArrowUp') {
            e.preventDefault();
            showSection(index - 1);
        }
    });
}

// свайпы — слушаем на карусели; работаем при фокусе или наведении
let startY = 0;
if (carousel) {
    carousel.addEventListener('touchstart', (e) => {
        if (document.activeElement !== carousel && !isPointerOver) return;
        startY = e.touches[0].clientY;
    });
    carousel.addEventListener('touchend', (e) => {
        if (document.activeElement !== carousel && !isPointerOver) return;
        const diff = e.changedTouches[0].clientY - startY;
        if (Math.abs(diff) < 50) return;
        if (diff < 0) showSection(index + 1);
        else showSection(index - 1);
    });
}

// ресайз — центрируем текущую секцию
window.addEventListener('resize', () => {
    // убрать анимацию при реcайзе, чтобы не дергалось
    const prevTransition = carousel.style.transition;
    carousel.style.transition = 'none';
    showSection(index);
    // восстановить переход асинхронно (чтобы showSection успел установить transform)
    requestAnimationFrame(() => {
        requestAnimationFrame(() => {
            carousel.style.transition = prevTransition || 'transform 0.35s cubic-bezier(0.65, 0, 0.35, 1)';
        });
    });
});
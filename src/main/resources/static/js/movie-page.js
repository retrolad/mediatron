// -------------------- Параллакс фонового изображения --------------------
// небольшой параллакс для .movie-backdrop при движении мыши
(function enableBackdropParallax() {
    const backdrop = document.querySelector('.movie-backdrop');
    if (!backdrop) return;
    console.log("параллакс")

    // не включаем эффект на тач/коарс-устройствах или на очень узких экранах
    if (window.matchMedia && window.matchMedia('(pointer: coarse)').matches) return;

    let targetX = 0, targetY = 0;
    let currentX = 0, currentY = 0;
    let rafId = null;
    const intensity = 10;    // макс смещение в px
    const lerpFactor = 0.09; // скорость сглаживания (меньше = медленнее)

    function onMouseMove(e) {
        if (window.innerWidth < 768) return; // отключаем на маленьких экранах
        const nx = (e.clientX / window.innerWidth - 0.5) * 2; // -1 .. 1
        const ny = (e.clientY / window.innerHeight - 0.5) * 2; // -1 .. 1

        targetX = nx * intensity;
        targetY = ny * intensity * 0.6; // по Y чуть меньше
        startLoop();
    }

    function startLoop() {
        if (rafId == null) rafId = requestAnimationFrame(loop);
    }

    function loop() {
        currentX += (targetX - currentX) * lerpFactor;
        currentY += (targetY - currentY) * lerpFactor;

        // небольшое увеличения масштаба для глубины
        const scale = 1.02;
        backdrop.style.transform = `translate3d(${currentX.toFixed(2)}px, ${currentY.toFixed(2)}px, 0) scale(${scale})`;

        // остановить rAF когда цель близка к нулю и уже в покое
        if (Math.abs(currentX - targetX) < 0.01 && Math.abs(currentY - targetY) < 0.01 && targetX === 0 && targetY === 0) {
            backdrop.style.transform = ''; // вернуть чистый стиль (позволяет transition плавно вернуть)
            cancelAnimationFrame(rafId);
            rafId = null;
            return;
        }
        rafId = requestAnimationFrame(loop);
    }

    function resetParallax() {
        targetX = 0;
        targetY = 0;
        startLoop();
    }

    // Наведение/уход курсора по области окна
    window.addEventListener('mousemove', onMouseMove, { passive: true });
    window.addEventListener('mouseleave', resetParallax);
    window.addEventListener('blur', resetParallax);
    window.addEventListener('resize', () => {
        // при ресайзе сбрасываем, чтобы не было сдвига на новых размерах
        resetParallax();
    });
})();

// -------------------- Плавный скролл между геро и контентом --------------------
(() => {
    const body = document.body;
    const docEl = document.documentElement;

    // Получаем высоту геро-блока из CSS-переменной
    function getHeroHeight() {
        const raw = getComputedStyle(docEl).getPropertyValue('--movie-hero-h').trim();
        if (!raw) return window.innerHeight;
        // Значение может быть в vh/dvh/svh - нам нужна текущая высота области
        if (raw.endsWith('vh') || raw.endsWith('dvh') || raw.endsWith('svh')) {
            return window.innerHeight; // адаптив на реальную высоту окна
        }
        const num = parseFloat(raw);
        return isNaN(num) ? window.innerHeight : num;
    }

    let heroH = getHeroHeight();
    window.addEventListener('resize', () => { heroH = getHeroHeight(); });

    let animFrame = null;
    let animating = false;
    let cancelToken = 0;

    function easeOutCubic(t) {
        return 1 - Math.pow(1 - t, 3);
    }

    function animatedScrollTo(targetY, duration = 650) {
        cancelToken++;
        const token = cancelToken;
        const startY = window.scrollY;
        const diff = targetY - startY;
        if (diff === 0) return;

        animating = true;
        docEl.classList.add('hero-animating');
        body.classList.add('hero-animating');

        const startTime = performance.now();

        function step(now) {
            if (token !== cancelToken) return; // отменено новой анимацией
            const t = Math.min(1, (now - startTime) / duration);
            const eased = easeOutCubic(t);
            window.scrollTo(0, startY + diff * eased);
            if (t < 1) {
                animFrame = requestAnimationFrame(step);
            } else {
                animating = false;
                docEl.classList.remove('hero-animating');
                body.classList.remove('hero-animating');
            }
        }
        animFrame = requestAnimationFrame(step);
    }

    function scrollDownFromHero() {
        animatedScrollTo(heroH - 1);
    }

    function scrollUpToHero() {
        animatedScrollTo(0);
    }

    // Wheel / touch logic
    function onWheel(e) {
        if (animating) return;
        const y = window.scrollY;
        const nearTop = y < heroH * 0.25;
        const inHeroZone = y < heroH - 10;
        const nearBodyStart = y > heroH * 0.35 && y < heroH * 0.9;

        // Прокрутка вниз из hero → переход к контенту
        if (e.deltaY > 0 && nearTop) {
            e.preventDefault();
            scrollDownFromHero();
        }
        // Прокрутка вверх, когда уже в основной зоне, но ещё недалеко от начала → назад к геро
        else if (e.deltaY < 0 && inHeroZone && y > heroH * 0.05 && nearBodyStart) {
            e.preventDefault();
            scrollUpToHero();
        }
    }

    let touchStartY = 0;
    function onTouchStart(e) {
        if (e.touches.length !== 1) return;
        touchStartY = e.touches[0].clientY;
    }
    function onTouchEnd(e) {
        if (animating) return;
        if (e.changedTouches.length !== 1) return;
        const endY = e.changedTouches[0].clientY;
        const delta = touchStartY - endY; // >0 значит свайп вверх
        const y = window.scrollY;

        // свайп вверх из верхней части геро
        if (delta > 40 && y < heroH * 0.25) {
            scrollDownFromHero();
        }
        // свайп вниз из начала основной части
        else if (delta < -40 && y < heroH && y > heroH * 0.25) {
            scrollUpToHero();
        }
    }

    // Обновляем состояние data-hero-scroll
    function updateState() {
        const y = window.scrollY;
        if (y < heroH * 0.5) {
            body.setAttribute('data-hero-scroll', 'idle');
        } else if (y < heroH - 10) {
            body.setAttribute('data-hero-scroll', 'transition');
        } else {
            body.setAttribute('data-hero-scroll', 'content');
        }
    }

    window.addEventListener('wheel', onWheel, { passive: false });
    window.addEventListener('touchstart', onTouchStart, { passive: true });
    window.addEventListener('touchend', onTouchEnd, { passive: true });
    window.addEventListener('scroll', () => {
        if (!animating) updateState();
    });

    // Первичная установка состояния
    updateState();
})();

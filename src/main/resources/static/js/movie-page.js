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
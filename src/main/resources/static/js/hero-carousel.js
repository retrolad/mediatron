(() => {
  let initialized = false;

  document.addEventListener('DOMContentLoaded', () => {
    if (initialized) return;
    initialized = true;

    const strip = document.getElementById('hero-strip');
    const bg = document.getElementById('hero-bg');
    const wrap = document.getElementById('hero-carousel');
    const info = document.getElementById('hero-info');
    const infoLogo = document.getElementById('hero-info-logo');
    const infoTitle = document.getElementById('hero-info-title');
    const infoYear = document.getElementById('hero-info-year');
    const infoAge = document.getElementById('hero-info-age');
    const infoRuntime = document.getElementById('hero-info-runtime');
    const infoDesc = document.getElementById('hero-info-description');

    if (!strip || !bg || !wrap) return;

    // Собираем исходные элементы (Thymeleaf)
    const originalNodes = Array.from(strip.querySelectorAll('.hc-item'));
    const count = originalNodes.length;
    if (!count) return;

    const getData = el => ({
      idx: parseInt(el.dataset.idx || '0', 10),
      title: el.dataset.title || '',
      poster: el.dataset.poster || '',
      backdrop: el.dataset.backdrop || ''
    });

    // Клонирование для бесконечности (3x лента)
    const cloneSide = (nodes, side) => {
      const frag = document.createDocumentFragment();
      nodes.forEach(n => {
        const c = n.cloneNode(true);
        c.dataset.clone = '1';
        frag.appendChild(c);
      });
      side === 'left' ? strip.insertBefore(frag, strip.firstChild) : strip.appendChild(frag);
    };
    if (count > 1) {
      cloneSide(originalNodes, 'left');
      cloneSide(originalNodes, 'right');
    }

    let nodes = Array.from(strip.children);
    let centerDomIndex = Math.max(0, count); // старт в среднем блоке
    let animating = false;

    // === Autoplay: каждые 5 секунд крутим на +1 ===
    const AUTOPLAY_MS = 5000;
    let autoplayTimer = null;
    const startAutoplay = () => {
      if (autoplayTimer || count <= 1) return;
      autoplayTimer = setInterval(() => {
        if (!animating) {
          // следующий DOM-индекс справа
          selectDomIndex(centerDomIndex + 1, true);
        }
      }, AUTOPLAY_MS);
    };
    const stopAutoplay = () => {
      if (autoplayTimer) {
        clearInterval(autoplayTimer);
        autoplayTimer = null;
      }
    };
    const resetAutoplay = () => {
      stopAutoplay();
      startAutoplay();
    };
    // === /Autoplay ===

    const setBackground = movie => {
      if (!movie?.backdrop) return;
      bg.style.backgroundImage =
        `linear-gradient(180deg, rgba(0,0,0,0.05), rgba(0,0,0,0.35)), url('${movie.backdrop}')`;
      bg.style.transform = 'scale(1.02)';
      requestAnimationFrame(() =>
        requestAnimationFrame(() => (bg.style.transform = 'scale(1)'))
      );
    };

    const updateInfo = (el) => {
      if (!info) return;
      const data = {
        title: el.dataset.title || '',
        logo: el.dataset.logo || '',
        year: el.dataset.year || '',
        age: el.dataset.age || '',
        runtime: el.dataset.runtime || '',
        description: el.dataset.description || ''
      };

      // Логотип или название
      if (infoLogo) {
        if (data.logo) {
          infoLogo.src = data.logo;
          infoLogo.style.display = '';
          if (infoTitle) infoTitle.style.display = 'none';
        } else {
          // нет логотипа — показываем название
          if (infoLogo) infoLogo.style.display = 'none';
          if (infoTitle) {
            infoTitle.textContent = data.title || '';
            infoTitle.style.display = '';
          }
        }
      } else if (infoTitle) {
        infoTitle.textContent = data.title || '';
      }

      if (infoYear) infoYear.textContent = data.year || '';
      if (infoAge) infoAge.textContent = data.age + '+' || '';
      if (infoRuntime) infoRuntime.textContent = data.runtime ? `${data.runtime} мин` : '';
      if (infoDesc) infoDesc.textContent = data.description || '';
    };

    // Центрирование по реальной геометрии (учитывает transform)
    const centerElement = (domIndex, animate = true) => {
      nodes = Array.from(strip.children);
      const target = nodes[domIndex];
      if (!target) return;

      const stripRect = strip.getBoundingClientRect();
      const itemRect = target.getBoundingClientRect();
      const targetCenter = (itemRect.left - stripRect.left) + itemRect.width / 2;
      const wrapCenter = wrap.clientWidth / 2;
      const shift = wrapCenter - targetCenter;

      if (!animate) {
        strip.style.transition = 'none';
        strip.style.transform = `translateX(${shift}px)`;
        // форсим reflow и возвращаем transition правило из CSS
        // чтобы следующая анимация сработала
        void strip.offsetWidth;
        requestAnimationFrame(() => (strip.style.transition = ''));
      } else {
        strip.style.transform = `translateX(${shift}px)`;
      }
    };

    // helper: выполнить действие на следующем кадре (после применения стилей/классов)
    const nextFrame = (cb) => requestAnimationFrame(() => requestAnimationFrame(cb));

    // {changed} normalize больше не трогает transform, только индекс
    const normalize = () => {
      if (count <= 1) return;
      if (centerDomIndex < count) {
        centerDomIndex += count;
      } else if (centerDomIndex >= 2 * count) {
        centerDomIndex -= count;
      }
    };

    const updateClasses = () => {
      nodes.forEach((el, i) => {
        el.classList.remove('active', 'near', 'far', 'dim', 'left', 'right');
        const delta = i - centerDomIndex;
        if (delta === 0) {
          el.classList.add('active');
        } else {
          const side = delta < 0 ? 'left' : 'right';
          const abs = Math.abs(delta);
          if (abs === 1) el.classList.add('near', side);
          else if (abs === 2) el.classList.add('far', side);
          else el.classList.add('dim', side); // dim скрыты в CSS
        }
      });
    };

    // Запрещаем запуск параллельной анимации, если предыдущая не завершилась
    const selectDomIndex = (domIndex, animate = true) => {
      if (animate && animating) return;
      centerDomIndex = Math.max(0, Math.min(domIndex, nodes.length - 1));
      const dataEl = nodes[centerDomIndex];
      const data = getData(dataEl);
      if (animate) animating = true;

      // Сначала классы, затем (на след. кадре) центрирование — без «дёрганий»
      updateClasses();
      if (animate) {
        nextFrame(() => centerElement(centerDomIndex, true));
      } else {
        centerElement(centerDomIndex, false);
      }

      setBackground(data);
      updateInfo(dataEl);
      // при ручном шаге перезапускаем авто‑прокрутку
      resetAutoplay();
    };

    const findNearestClone = origIdx => {
      let best = centerDomIndex;
      let bestDist = Infinity;
      nodes.forEach((el, i) => {
        if (parseInt(el.dataset.idx || '-1', 10) === origIdx) {
          const d = Math.abs(i - centerDomIndex);
          if (d < bestDist) {
            bestDist = d;
            best = i;
          }
        }
      });
      return best;
    };

    const selectOriginal = origIdx => {
      if (animating) return;
      const domIndex = findNearestClone(origIdx);
      selectDomIndex(domIndex, true);
    };

    // Клики по постерам
    strip.addEventListener('click', e => {
      const li = e.target.closest('.hc-item');
      if (!li || !strip.contains(li)) return;
      const origIdx = parseInt(li.dataset.idx || '0', 10);
      const domIndex = findNearestClone(origIdx);
      const distance = domIndex - centerDomIndex;
      // Если клик по уже активному — просто фон обновим
      if (distance === 0) {
        setBackground(getData(li));
        updateInfo(li);
        resetAutoplay();
        return;
      }
      selectOriginal(origIdx);
    });

    // Наведение/уход курсора — пауза/продолжение автопрокрутки
    let pointerOver = false;
    wrap.addEventListener('pointerenter', () => {
      pointerOver = true;
      stopAutoplay();
    });
    wrap.addEventListener('pointerleave', () => {
      pointerOver = false;
      startAutoplay();
    });

    // Клавиши
    document.addEventListener('keydown', e => {
      if (!pointerOver || animating) return;
      if (e.key === 'ArrowRight') {
        selectDomIndex(centerDomIndex + 1, true);
      } else if (e.key === 'ArrowLeft') {
        selectDomIndex(centerDomIndex - 1, true);
      } else return;
      // сбрасываем таймер при ручной навигации
      resetAutoplay();
    });

    // Завершение анимации
    strip.addEventListener('transitionend', e => {
      if (e.target !== strip || e.propertyName !== 'transform') return;
      // 1) Только меняем индекс (без изменения transform)
      normalize();
      // 2) На следующем кадре мгновенно совмещаем центр с эквивалентным элементом среднего блока
      nextFrame(() => {
        centerElement(centerDomIndex, false);
        // 3) И уже после фиксации центра — обновляем классы (чтобы не было кадра с dim в центре)
        nextFrame(() => {
          updateClasses();
          // после окончательной фиксации центра — обновим инфо по новому центральному
          const el = nodes[centerDomIndex];
          if (el) updateInfo(el);
          animating = false;
        });
      });
    });

    // Пауза автоплея при скрытии вкладки
    document.addEventListener('visibilitychange', () => {
      if (document.hidden) stopAutoplay();
      else startAutoplay();
    });

    // Инициализация
    const initAfterLayout = (attempt = 0) => {
      nodes = Array.from(strip.children);
      const ok = wrap.clientWidth > 0 && nodes.length > 0 && nodes[0].getBoundingClientRect().width > 0;
      if (ok || attempt > 60) {
        updateClasses();
        nextFrame(() => centerElement(centerDomIndex, false));
        const el = nodes[centerDomIndex];
        setBackground(getData(el));
        updateInfo(el);
        // стартуем автоплей
        startAutoplay();
        return;
      }
      requestAnimationFrame(() => initAfterLayout(attempt + 1));
    };
    initAfterLayout();

    // Ресайз
    let resizeRaf = 0;
    window.addEventListener('resize', () => {
      cancelAnimationFrame(resizeRaf);
      resizeRaf = requestAnimationFrame(() => {
        updateClasses();
        nextFrame(() => centerElement(centerDomIndex, false));
        const el = nodes[centerDomIndex];
        if (el) updateInfo(el);
      });
    });
  });
})();

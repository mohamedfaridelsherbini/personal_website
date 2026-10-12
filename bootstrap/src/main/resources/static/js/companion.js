// Contact companion: docked avatar with contact menu, plus a bounded welcome shown once per tab session.
(function () {
    'use strict';

    var root = document.querySelector('[data-companion]');
    if (!root) return;

    var button = root.querySelector('[data-companion-button]');
    var popover = root.querySelector('[data-companion-popover]');
    var avatar = root.querySelector('[data-companion-avatar]');
    var SHEET = '/static/images/companion-sheet.webp';
    var STORAGE_KEY = 'pw-welcome-seen';
    var reducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;
    var restExpression = 'neutral';

    function setExpression(name) {
        avatar.setAttribute('data-expression', name);
    }

    function openMenu() {
        popover.hidden = false;
        button.setAttribute('aria-expanded', 'true');
        restExpression = 'happy';
        setExpression('happy');
    }

    function closeMenu(returnFocus) {
        popover.hidden = true;
        button.setAttribute('aria-expanded', 'false');
        restExpression = 'neutral';
        setExpression('neutral');
        if (returnFocus) button.focus();
    }

    button.addEventListener('click', function () {
        if (popover.hidden) openMenu(); else closeMenu(false);
    });
    popover.addEventListener('click', function (event) {
        if (event.target.closest('a')) closeMenu(false);
    });
    popover.querySelectorAll('a').forEach(function (link) {
        link.addEventListener('pointerenter', function () { setExpression('wink'); });
        link.addEventListener('pointerleave', function () { setExpression(restExpression); });
    });
    document.addEventListener('keydown', function (event) {
        if (event.key === 'Escape' && !popover.hidden) closeMenu(true);
    });
    document.addEventListener('pointerdown', function (event) {
        if (!popover.hidden && !root.contains(event.target)) closeMenu(false);
    });

    function eligibleForWelcome() {
        if (root.getAttribute('data-welcome') !== 'true') return false;
        if (reducedMotion || window.location.hash) return false;
        if (window.innerWidth < 320 || window.innerHeight < 480) return false;
        try {
            if (window.sessionStorage.getItem(STORAGE_KEY)) return false;
            window.sessionStorage.setItem(STORAGE_KEY, '1');
        } catch (e) {
            return false;
        }
        return true;
    }

    function runWelcome() {
        var layer = document.querySelector('[data-companion-welcome]');
        if (!layer) return;
        var big = layer.querySelector('[data-welcome-avatar]');
        var docked = false;
        var timers = [];

        function dock(instant) {
            if (docked) return;
            docked = true;
            timers.forEach(clearTimeout);
            removeSkipListeners();
            var from = big.getBoundingClientRect();
            var to = avatar.getBoundingClientRect();
            var finish = function () {
                layer.hidden = true;
                root.classList.remove('is-waiting');
                setExpression('neutral');
            };
            if (instant || !from.width) {
                finish();
                return;
            }
            layer.classList.add('is-docking');
            big.style.transformOrigin = 'top left';
            big.style.transition = 'transform 600ms cubic-bezier(0.22, 0.8, 0.2, 1)';
            big.style.transform = 'translate(' + (to.left - from.left) + 'px,' + (to.top - from.top) + 'px) scale(' + (to.width / from.width) + ')';
            timers.push(setTimeout(finish, 620));
        }

        function skip() { dock(true); }
        function onKey(event) { if (event.key === 'Escape' || event.key === 'Enter' || event.key === ' ') skip(); }
        function removeSkipListeners() {
            window.removeEventListener('wheel', skip);
            window.removeEventListener('touchstart', skip);
            window.removeEventListener('keydown', onKey);
            layer.removeEventListener('click', skip);
        }

        window.addEventListener('wheel', skip, { passive: true });
        window.addEventListener('touchstart', skip, { passive: true });
        window.addEventListener('keydown', onKey);
        layer.addEventListener('click', skip);

        layer.hidden = false;
        root.classList.add('is-waiting');
        requestAnimationFrame(function () { layer.classList.add('is-visible'); });
        timers.push(setTimeout(function () { dock(false); }, 180 + 700));
    }

    if (eligibleForWelcome()) {
        var probe = new Image();
        var settled = false;
        var timeout = setTimeout(function () { settled = true; }, 1200);
        probe.onload = function () {
            clearTimeout(timeout);
            if (!settled) runWelcome();
        };
        probe.onerror = function () { clearTimeout(timeout); };
        probe.src = SHEET;
    }
})();

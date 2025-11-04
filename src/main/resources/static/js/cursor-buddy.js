window.addEventListener('DOMContentLoaded', () => {
  const svgNS = 'http://www.w3.org/2000/svg';
  const svg = document.createElementNS(svgNS, 'svg');
  svg.classList.add('cursor-line-svg');

  const defs = document.createElementNS(svgNS, 'defs');
  const gradient = document.createElementNS(svgNS, 'linearGradient');
  gradient.id = 'cursor-trail-gradient';
  gradient.setAttribute('x1', '0%');
  gradient.setAttribute('y1', '0%');
  gradient.setAttribute('x2', '100%');
  gradient.setAttribute('y2', '0%');

  const stopStart = document.createElementNS(svgNS, 'stop');
  stopStart.setAttribute('offset', '0%');
  stopStart.setAttribute('stop-color', 'rgba(56, 224, 123, 0)');

  const stopMid = document.createElementNS(svgNS, 'stop');
  stopMid.setAttribute('offset', '60%');
  stopMid.setAttribute('stop-color', 'rgba(56, 224, 123, 0.8)');

  const stopEnd = document.createElementNS(svgNS, 'stop');
  stopEnd.setAttribute('offset', '100%');
  stopEnd.setAttribute('stop-color', 'rgba(255, 255, 255, 1)');

  gradient.append(stopStart, stopMid, stopEnd);
  defs.appendChild(gradient);
  svg.appendChild(defs);

  const path = document.createElementNS(svgNS, 'path');
  path.classList.add('cursor-line-path');
  path.setAttribute('stroke', 'url(#cursor-trail-gradient)');
  svg.appendChild(path);
  document.body.appendChild(svg);

  const points = [];
  const maxPoints = 20;
  let hideTimeout;
  let needsUpdate = false;

  const resize = () => {
    svg.setAttribute('width', window.innerWidth);
    svg.setAttribute('height', window.innerHeight);
    svg.setAttribute('viewBox', `0 0 ${window.innerWidth} ${window.innerHeight}`);
  };

  const buildSmoothPath = () => {
    if (points.length < 2) {
      path.setAttribute('d', '');
      return;
    }

    let d = `M ${points[0].x} ${points[0].y}`;
    for (let i = 1; i < points.length; i += 1) {
      const prev = points[i - 1];
      const curr = points[i];
      const midX = (prev.x + curr.x) / 2;
      const midY = (prev.y + curr.y) / 2;
      d += ` Q ${prev.x} ${prev.y} ${midX} ${midY}`;
    }

    const last = points[points.length - 1];
    d += ` T ${last.x} ${last.y}`;
    path.setAttribute('d', d);
  };

  const requestPathUpdate = () => {
    if (needsUpdate) return;
    needsUpdate = true;
    requestAnimationFrame(() => {
      buildSmoothPath();
      needsUpdate = false;
    });
  };

  const showTrail = () => {
    svg.classList.add('is-visible');
    clearTimeout(hideTimeout);
    hideTimeout = setTimeout(() => svg.classList.remove('is-visible'), 400);
  };

  window.addEventListener('mousemove', (event) => {
    const point = { x: event.clientX, y: event.clientY };
    points.push(point);
    if (points.length > maxPoints) {
      points.shift();
    }
    requestPathUpdate();
    showTrail();
  });

  window.addEventListener('mouseleave', () => {
    svg.classList.remove('is-visible');
  });

  window.addEventListener('resize', resize);
  resize();
});

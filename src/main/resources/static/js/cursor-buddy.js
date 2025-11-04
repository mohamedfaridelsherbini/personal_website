(() => {
  const cursor = document.createElement('div');
  cursor.className = 'cursor-buddy';
  cursor.innerHTML = '<span>🕺</span>';
  document.body.appendChild(cursor);

  let x = window.innerWidth / 2;
  let y = window.innerHeight / 2;
  let targetX = x;
  let targetY = y;
  let rafId;

  const move = (event) => {
    targetX = event.clientX;
    targetY = event.clientY;
    cursor.classList.add('is-visible');
  };

  const leave = () => {
    cursor.classList.remove('is-visible');
  };

  const render = () => {
    x += (targetX - x) * 0.15;
    y += (targetY - y) * 0.15;
    cursor.style.transform = `translate3d(${x}px, ${y}px, 0)`;
    rafId = requestAnimationFrame(render);
  };

  window.addEventListener('mousemove', move);
  window.addEventListener('mouseout', leave);
  render();
})();

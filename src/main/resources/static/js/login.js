document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('form[action="/login"]');
  if (!form) return;

  let submitting = false;
  form.addEventListener('submit', (e) => {
    if (submitting) {
      e.preventDefault();
      return;
    }
    submitting = true;
    // ...existing code...
  });
});

$(document).ready(function() {
    $(document).on('keyup', '#titulo', function() {
      $('#autofill_txt').text($(this).val());
    });
    $(document).on('change', 'input[name=radiobtn]', function() {
      $('#autofill_radio').text($(this).val());
    });
    $(document).on('keyup', '#txtarea', function() {
      $('#autofill_txtarea').text($(this).val());
    });
  });
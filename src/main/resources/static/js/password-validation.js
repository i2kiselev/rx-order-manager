$(document).ready(function () {
  $("#registerForm").submit(function (event) {
    $("#con").remove();
    let confirmPass = $("#confirm");
    let actualPass = $("#password");
    console.log(actualPass.val());
    console.log(confirmPass.val());
    if (actualPass.val() !== confirmPass.val()) {
      confirmPass.after(
        '<p class="error" id="con" >Passwords don\'t match</p>'
      );
      event.preventDefault();
    }
  });
});

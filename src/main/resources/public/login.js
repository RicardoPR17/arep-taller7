function login() {
  let msgVar = document.getElementById("msg").value;
  const xhttp = new XMLHttpRequest();
  xhttp.onload = function () {
    document.getElementById("logmsg").innerHTML = this.responseText;
  };
  xhttp.open("GET", "/logservicefacade?msg=" + msgVar);
  xhttp.send();
}

function loadPostMsg(user, pass) {
  let url = "/login?user=" + user.value + "&pass=" + pass.value;

  fetch(url, { method: "POST" })
    .then((x) => x.text())
    .then((y) => (document.getElementById("postrespmsg").innerHTML = y));
}

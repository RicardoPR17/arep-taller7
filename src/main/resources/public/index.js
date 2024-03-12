function login(user, pass) {
  let url = "/login?user=" + user.value + "&pass=" + pass.value;

  fetch(url, { method: "POST" })
    .then((x) => x.text())
    .then((y) => (document.getElementById("logmsg").innerHTML = y));
}

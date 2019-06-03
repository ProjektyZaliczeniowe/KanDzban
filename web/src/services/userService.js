export const userService = {
  login,
  logout
};

const apiUrl = "http://peaceful-sierra-14544.herokuapp.com";

function login(login, password) {
  const requestOptions = {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ login, password })
  };

  return fetch(apiUrl + "/login", requestOptions)
    .then(handleResponse)
    .then(user => {
      if (user) {
        localStorage.setItem("login", login);
      }
      return user;
    });
}

function logout() {
  localStorage.removeItem("login");
}

function handleResponse(response) {
  return response.text().then(text => {
    const data = text && JSON.parse(text);
    if (!response.ok) {
      if (response.status === 401) {
        // auto logout if 401 response returned from api
        logout();
        // location.reload(true);
      }
      const error = data.errors[0];
      return Promise.reject(error);
    }

    return data;
  });
}

export const userService = {
  login,
  logout,
  register
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

function register(login, email, password) {
  const requestOptions = {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ login, email, password })
  };

  return fetch(apiUrl + "/register", requestOptions)
    .then(handleResponse)
    .then(user => {
      return user;
    });
}

function handleResponse(response) {
  return response.text().then(text => {
    const data = text && JSON.parse(text);
    if (!response.ok) {
      if (response.status === 401) {
        logout();
      }
      let error = "";
      data.errors.forEach(element => {
        error += element + "\t";
      });
      return Promise.reject(error);
    }

    return data;
  });
}

// export const boardService = {
//   getBoard,
//   getUserBoards
// };

// const apiUrl = "https://kan-ban-server.herokuapp.com/";

// function getUserBoards() {
//   const requestOptions = {
//     method: "GET",
//     headers: { "Content-Type": "application/json" }
//   };

//   return fetch(apiUrl + "/boards", requestOptions)
//     .then(handleResponse)
//     .then(user => {
//       return user;
//     });
// }

// function getBoard(id) {
//   const requestOptions = {
//     method: "GET",
//     headers: { "Content-Type": "application/json" }
//   };

//   return fetch(apiUrl + "/boards/" + id, requestOptions)
//     .then(handleResponse)
//     .then(user => {
//       return user;
//     });
// }

// function logout() {
//   localStorage.removeItem("login");
// }

// function register(login, email, password) {
//   const requestOptions = {
//     method: "POST",
//     headers: { "Content-Type": "application/json" },
//     body: JSON.stringify({ login, email, password })
//   };

//   return fetch(apiUrl + "/register", requestOptions)
//     .then(handleResponse)
//     .then(user => {
//       return user;
//     });
// }

// function handleResponse(response) {
//   return response.text().then(text => {
//     const data = text && JSON.parse(text);
//     if (!response.ok) {
//       if (response.status === 401) {
//         logout();
//       }
//       let error = "";
//       data.errors.forEach(element => {
//         error += element + "\t";
//       });
//       return Promise.reject(error);
//     }

//     return data;
//   });
// }

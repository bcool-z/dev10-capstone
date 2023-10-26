const url = `${process.env.REACT_APP_URL}/reservation`;


export async function findResByUserId(id) {

    const response = await fetch(`${url}/student/${id}`);
      if (response.status === 200) {
        return response.json();
      } else if (response.status === 404) {
        return Promise.reject(`User id: ${id} could not be found.`);
      } else {
        return Promise.reject("Unexpected error.");
      }
    
    }


export async function makeReservation(reservation) {
    const jwtToken = localStorage.getItem("jwt_token");
    if (!jwtToken) {
      return Promise.reject("Unauthorized.");
    }
  
    const init = {
      headers: {
        "Content-Type": "application/json",
        'Accept': "application/json",
        Authorization: "Bearer " + jwtToken,
      },
      body: JSON.stringify(reservation.id),
    };
  
    if (reservation.id > 0) {
      init.method = "PUT";
      const response = await fetch(`${url}/${reservation.id}`, init);
      if (response.status === 400) {
        const result = await response.json();
        return { errors: result.messages };
      } else if (response.status === 404) {
        return Promise.reject(
          `Reservation id: ${reservation.id} could not be found.`
        );
      } else if (response.status !== 204) {
        return Promise.reject("Unexpected error, oops.");
      }
    } else {
      init.method = "POST";
      const response = await fetch(url, init);
      if (response.status === 201) {
        return response.json();
      } else if (response.status === 400) {
        const result = await response.json();
        return { errors: result.messages };
      } else {
        return Promise.reject("Unexpected error, oops.");
      }
    }
  }
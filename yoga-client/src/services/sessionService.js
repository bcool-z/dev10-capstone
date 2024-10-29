const url = `${process.env.REACT_APP_URL}/session`;

export async function fetchSessionsByDate(selectedDate){
        try {
          const response = await fetch(`http://localhost:8080/session/date/${selectedDate}`);
          if (response.ok) {
            const data = await response.json();
            return(data);
          } else {
            console.error('Error fetching classes');
          }
        } catch (error) {
          console.error('Error fetching classes:', error);
        }


};

export async function saveSession(session) {
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
      body: JSON.stringify(session),
    };
  
    if (session.id > 0) {
      init.method = "PUT";
      const response = await fetch(`${url}/${session.id}`, init);
      if (response.status === 400) {
        const result = await response.json();
        return { errors: result.messages };
      } else if (response.status === 404) {
        return Promise.reject(
          `session id: ${session.id} could not be found.`
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
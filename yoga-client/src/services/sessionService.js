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

export async function getSessionById(id){
  try{
    const response = await fetch(`${url}/${id}`);
    if (response.ok) {
      const data = await response.json();
      return(data);
    } else {
      console.error('error fetching class')
    }
  } catch (error) {
    console.error('Error fecthing class...', error);
  }
  }


export async function getEnrollmentCount(id){
  try {
    const response = await fetch(`http://localhost:8080/session/count/${id}`);
    if (response.ok) {
      const data = await response.json;
      return(data);
    } else {
      console.error('Error fetching classes');
    }
  } catch (error) {
    console.error('Error fetching classes:', error);
  }
};



// saveSession Function
export async function saveSession(session) {
  const jwtToken = localStorage.getItem("jwt_token");
  if (!jwtToken) {
    return { errors: ["Unauthorized"] };
  }

  const init = {
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      Authorization: "Bearer " + jwtToken,
    },
    body: JSON.stringify(session),
  };

  try {
    init.method = session.id > 0 ? "PUT" : "POST";
    const urlWithId = session.id > 0 ? `${url}/${session.id}` : url;
    const response = await fetch(urlWithId, init);

    if (response.ok) {
      return await response.json();
    } else if (response.status === 400 || response.status === 412) {
      const result = await response.json();
      return { errors: result.messages || ["Validation error occurred."] };
    } else if (response.status === 404) {
      return { errors: [`Session id: ${session.id} not found`] };
    } else {
      return { errors: ["Unexpected error, oops."] };
    }
  } catch (error) {
    console.error("Error in saveSession:", error);
    return { errors: ["Network or server error."] };
  }
}



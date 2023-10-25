const url = process.env.REACT_APP_URL;

export async function findUserById(id) {

const response = await fetch(`${url}/${id}`);
  if (response.status === 200) {
    return response.json();
  } else if (response.status === 404) {
    return Promise.reject(`User id: ${id} could not be found.`);
  } else {
    return Promise.reject("Unexpected error.");
  }

}

export async function findUserByEmail(email) {

    const response = await fetch(`${url}/user/email/${email}`);
      if (response.status === 200) {
        return response.json();
      } else if (response.status === 404) {
        return Promise.reject(`User id: ${email} could not be found.`);
      } else {
        return Promise.reject("Unexpected error.");
      }
    
    }
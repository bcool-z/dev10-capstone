const url = `${process.env.REACT_APP_URL}/location`;

export async function fetchLocations() {

    const response = await fetch(url);
    if (response.status === 200)
        return response.json();
    else if (response.status === 404)
        return Promise.reject("locations not found");
    else return Promise.reject("unexpected error");
}

export async function saveLocation(location){

    const jwtToken = localStorage.getItem("jwt_token");
    if (!jwtToken){
        return Promise.reject("Unauthorized");
    }

    const init = {
        headers: {
            "Content-Type": "application/json",
        'Accept': "application/json",
        Authorization: "Bearer " + jwtToken,
        },
        body: JSON.stringify(location)
    };

    try{
    init.method = location.id > 0 ? "PUT" : "POST";
    const urlWithId = location.id > 0 ? `${url}/${location.id}` : url;
    const response = await fetch(urlWithId,init);
    
        if(response.ok){
            return await response.json();
        }
        else {const result = await response.json();
            return {errors: result.messages || "Validation Error"}
        }
    }
    catch(error){
        console.error("Error in saveSession:", error);
        return { errors: ["Unknown Error"] };
      }
}


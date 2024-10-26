const url = `${process.env.REACT_APP_URL}/location`;

export async function fetchLocations() {

    const response = await fetch(url);
    if (response.status === 200)
        return response.json();
    else if (response.status === 404)
        return Promise.reject("locations not found");
    else return Promise.reject("unexpected error");
}


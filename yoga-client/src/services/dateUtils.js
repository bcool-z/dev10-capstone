export const formatDate = (date) => {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Add 1 to the month because it is 0-based
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;};


export const formattedTime = (dateTime) => {
        return new Date(dateTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: true });
        
        }
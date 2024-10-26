// export const formatDate = (date) => {
//     if(!date) return null;
//     const year = date.getFullYear();
//     const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Add 1 to the month because it is 0-based
//     const day = date.getDate().toString().padStart(2, '0');
//     return `${year}-${month}-${day}`;};

export function formatDate(date) {
    if (!date) return null;
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Add leading zero
    const day = String(date.getDate()).padStart(2, '0'); // Add leading zero
    return `${year}-${month}-${day}`;
  }

export const formattedTime = (dateTime) => {
        return new Date(dateTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: true });
        
        }

// export const stringToDate = (dateString) =>{
//     return new Date(dateString);
// }
export const stringToDate = (dateString) => {
    const date = new Date(dateString);
    return !isNaN(date) ? date : null;  // Return null for invalid dates
  };
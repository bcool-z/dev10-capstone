import { useEffect, useState, useCallback } from "react";

export default function Profile({ user }) {
    const [isEditing, setIsEditing] = useState(false);
    const [editedUser, setEditedUser] = useState({ ...user });
  
    const handleEditClick = () => {
      setIsEditing(true);
    };
  
    const handleSaveClick = () => {
      // Update the user's information on save
      // You may want to send this data to a server or update your state management here
      // For simplicity, we'll just update the local state
    //   setUser(editedUser);
      setIsEditing(false);
    };
  
    return (
      <div>
        <h2>User Profile</h2>
        {isEditing ? (
          <div>
            <label>First Name:</label>
            <input
              type="text"
              value={editedUser.firstName}
              onChange={(e) => setEditedUser({ ...editedUser, firstName: e.target.value })}
            />
            <br />
            <label>Last Name:</label>
            <input
              type="text"
              value={editedUser.lastName}
              onChange={(e) => setEditedUser({ ...editedUser, lastName: e.target.value })}
            />
            <br />
            {/* Add more fields for editing */}
            <button onClick={handleSaveClick}>Save</button>
          </div>
        ) : (
          <div>
            <p>First Name: {user.firstName}</p>
            <p>Last Name: {user.lastName}</p>
            {/* Display more user details */}
            <button onClick={handleEditClick}>Edit</button>
          </div>
        )}
      </div>
    );
  }
  
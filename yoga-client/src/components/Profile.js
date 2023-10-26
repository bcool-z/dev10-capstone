import { useEffect, useState, useCallback, useContext } from "react";
import AuthContext from "../contexts/AuthContext";
import { findUserById } from "../services/userService";
import { findUserByEmail } from "../services/userService";
import { findResByUserId } from "../services/resService";

export default function Profile() {
  const { user } = useContext(AuthContext);

  // const [appUser, setAppUser] = useState("");
  const [isEditing, setIsEditing] = useState(false);
  const [editedUser, setEditedUser] = useState({ ...user });

  const [reservations, setReservations] = useState([])

  useEffect(() => {
    if (user) {
      console.log(user.userId)
      findResByUserId(user.userId).then(setReservations);
    }
  }, []);


  // useEffect(() => {
  //   if (user) {
  //     findUserByEmail(user.username).then(setAppUser);
  //   }
  // }, [user]);

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

    <>
    <div>
      <h2>User Profile</h2>
      {isEditing ? (
        <div>
          <label>First Name:</label>
          <input
            type="text"
            value={editedUser.firstName}
            onChange={(e) =>
              setEditedUser({ ...editedUser, firstName: e.target.value })
            }
          />
          <br />
          <label>Last Name:</label>
          <input
            type="text"
            value={editedUser.lastName}
            onChange={(e) =>
              setEditedUser({ ...editedUser, lastName: e.target.value })
            }
          />
          <br />
          <label>Date of Birth:</label>
          <input
            type="text"
            value={editedUser.dob}
            onChange={(e) =>
              setEditedUser({ ...editedUser, lastName: e.target.value })
            }
          />
          <br />
          <label>Phone Number:</label>
          <input
            type="text"
            value={editedUser.phoneNumber}
            onChange={(e) =>
              setEditedUser({ ...editedUser, lastName: e.target.value })
            }
          />
          <br />
          <label>Email Address:</label>
          <input
            type="text"
            value={editedUser.emailAddress}
            onChange={(e) =>
              setEditedUser({ ...editedUser, lastName: e.target.value })
            }
          />
          <br />
          {/* Add more fields for editing */}
          <button onClick={handleSaveClick}>Save</button>
        </div>
      ) : (
        <div>
          <p>First Name: {user.firstName}</p>
          <p>Last Name: {user.lastName}</p>
          <p>Date of Birth: {user.dob}</p>
          <p>Phone Number: {user.phoneNumber}</p>
          <p>Email: {user.username}</p>
          {/* Display more user details */}
          <button onClick={handleEditClick}>Edit</button>
        </div>
      )}
    </div>

    <div>
        <h2>Classes Signed up for:</h2>

        
      {reservations.length === 0 ? (
        <p></p>
      ) : (
        <ul>
          {reservations.map((res) => (
            <li key={res.id}>{res.session.start}</li>
          ))}
        </ul>
      )}
    </div>


    </>
  );
}

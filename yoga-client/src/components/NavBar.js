import { useContext } from "react";
import { NavLink, Link, useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";
import 'bootstrap/dist/css/bootstrap.min.css';

function NavBar() {
  const { appUser, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <header>
  <nav className="navbar navbar-expand-lg navbar-light">
    <NavLink className="navbar-brand" to="/">
      Yoga
    </NavLink>
    <button
      className="navbar-toggler"
      type="button"
      data-toggle="collapse"
      data-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span className="navbar-toggler-icon"></span>
    </button>
    <div className="collapse navbar-collapse">
      <ul className="navbar-nav me-auto">
        <li className="nav-item">
          <NavLink className="nav-link" to="/about">
            About
          </NavLink>
        </li>
        <li className="nav-item">
          <NavLink className="nav-link" to="/schedule">
            Schedule
          </NavLink>
        </li>
        {appUser && appUser.userType=== 'INSTRUCTOR' && (
          <li className="nav-item">
              <NavLink className="nav-link" to="/manage">
                Instructor Dashboard
              </NavLink>
          </li>
        )}
      </ul>
      <ul className="navbar-nav "> 
      {appUser && (
          <li className="nav-item">
            <NavLink className="nav-link" to={`/profile/${appUser.id}`}>
              Profile
            </NavLink>
          </li>
        )}
        {!appUser ? (
          <>
            <li className="nav-item">
              <Link to="/login" className="nav-link">
                Login
              </Link>
            </li>
            <li className="nav-item">
              <Link to="/signup" className="nav-link">
                Sign Up
              </Link>
            </li>
          </>
        ) : (
          <>
            <li className="nav-item">
              <span className="badge rounded-pill text-bg-info">
                {appUser.username}
              </span>
            </li>
            <li className="nav-item">
              <button
                onClick={handleLogout}
                className="btn btn-outline-secondary btn-sm"
              >
                Log out
              </button>
            </li>
          </>
        )}
      </ul>
    </div>
  </nav>
</header>
  );
}

export default NavBar;
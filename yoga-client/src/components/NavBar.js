import { useContext } from "react";
import { NavLink, Link, useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";

function NavBar() {
  const { user, logout } = useContext(AuthContext);
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
          <ul className="navbar-nav ml-auto"> {/* Use ml-auto to push items to the right */}
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
            {user && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/Profile">
                  Profile
                </NavLink>
              </li>
            )}
          </ul>
          {!user && (
            <div className="ml-auto"> {/* Use ml-auto to push items to the right */}
              <Link to="/login">Login</Link>
              {'  |  '}
              <Link to="/signup">Sign Up</Link>
            </div>
          )}
          {user && (
            <div className="ml-auto"> {/* Use ml-auto to push items to the right */}
              <span className="badge rounded-pill text-bg-info">
                {user.username}
              </span>
              {}
              <button
                onClick={handleLogout}
                className="btn btn-outline-secondary btn-sm"
              >
                Log out
              </button>
            </div>
          )}
        </div>
      </nav>
    </header>
  );
}

export default NavBar;
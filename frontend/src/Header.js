import React, {Component} from "react";
import { Link} from "react-router-dom";
import './css/frontpage_style.css';

class Header extends Component {
    constructor() {
        super();
        let loggedIn = false;
        if (localStorage.getItem('loggedin') === 'true') {
            loggedIn = true;
        }
        this.state = {loggedIn: loggedIn};

        this.logout = this.logout.bind(this);
    }

    logout() {
        localStorage.removeItem('userId');
        localStorage.removeItem('loggedin');
        localStorage.removeItem('userType');
        localStorage.removeItem('username');
        this.setState({loggedIn: false});
    }

    render() {
        if (!this.state.loggedIn && localStorage.getItem('loggedin') === 'true') {
            this.setState({loggedIn: true})
        }
        let windowWidth = window.innerWidth;
        let welcomeMessage = 'Welcome, ' + localStorage.getItem('username') + '!';

        return (
            <div>
                {this.state.loggedIn ?
                <nav className="header">
                    <Link to="/" className="frontpagelink"><i className='fab fa-earlybirds'></i>{windowWidth >= 500 && "Blog Potoo"}</Link>
                    <Link to="/" onClick={this.logout} className="registerlink">{windowWidth >= 500 ? "Logout" : <i className='fas fa-sign-out-alt'></i>}</Link>
                    <Link to="/blogposts/modifypost/new" className="newpostlink">{windowWidth >= 500 ? "New post" : <i className='fas fa-edit'></i>}</Link>
                    <Link to="/" className="userpagelink">{windowWidth >= 500 ? welcomeMessage : <i className='fas fa-user-alt'></i>}</Link>
                </nav>
                :
                 <nav className="header">
                    <Link to="/"><i className='fab fa-earlybirds'></i>{windowWidth >= 500 && "Blog Potoo"}</Link>
                    <Link to="/register" className="registerlink">Register</Link>
                    <Link to="/login" className="loginlink">LogIn</Link>
                </nav>}
            </div>
        );
    }
}

export default Header;
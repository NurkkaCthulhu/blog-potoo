import React, {Component} from "react";
import ErrorPage from "./ErrorPage";
import './css/AdminPage_style.css';

class AdminPage extends Component {
    constructor() {
        super();

        this.updateUserList = this.updateUserList.bind(this);

        this.state = {
            users: []
            , adminAccess: true
        }
    }

    componentDidMount() {
        if(localStorage.getItem('userType') === 'ADMIN') {
            this.updateUserList();
        } else {
            console.log('not admin')
            this.setState({adminAccess: false});
        }
    }

    deleteUser = (event) => {
        let user = event.target;

        fetch('/api/users/' + user.id,{
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            console.log('done');
            this.updateUserList();
        });

        event.preventDefault();
    };

    updateUserList() {
        fetch('/api/users')
            .then((httpResp) => httpResp.json())
            .then((users) => this.setState({users: users}));
    }

    render() {
        return (
            <div>
                {this.state.adminAccess ?
                    <div className={"allUserscontainer"}>
                        <h1 className="manageTitle">Manage users</h1>
                        <table>
                            <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Usertype</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.users.map((user) =>
                                    <tr key={user.id}>
                                        {console.log('checking usertype: ',user.userType)}
                                        <td className="username">{user.username}</td>
                                        <td className="usertype">{user.userType}</td>
                                        {user.username !== localStorage.getItem('username') && user.userType !== 'DELETED'  ?
                                            <td className="deleteUser" id={user.id} onClick={this.deleteUser}>Delete user</td>
                                            :
                                            <td></td>
                                        }

                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>
                    :
                    <ErrorPage message={"Access denied. This page is only for admins."}/>
                }
            </div>
        );
    }
}

export default AdminPage;
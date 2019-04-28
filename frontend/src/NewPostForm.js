import React, {Component} from "react";
import {withRouter} from "react-router-dom";
import './css/NewPostForm_style.css';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import { EditorState, convertToRaw, ContentState } from 'draft-js';
import { Editor } from 'react-draft-wysiwyg';
import draftToHtml from 'draftjs-to-html';
import htmlToDraft from 'html-to-draftjs';
import ErrorPage from './ErrorPage.js';

class NewPostForm extends Component {

    constructor(props) {
        super(props);

        this.makeNewPost = this.makeNewPost.bind(this);
        this.deletePost = this.deletePost.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onRouteChanged = this.onRouteChanged.bind(this);

        this.state = {
            formTitle: 'Loading...'
            , author: ''
            , title: ''
            , content: ''
            , maxContentLength: 15000
            , tags: ''
            , editorState: EditorState.createEmpty()
            , postFound: true
            , errorMessage: ''
        };
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        //const tags = target.tags;

        this.setState({
            [name]: value
        });
    }

    onEditorStateChange = (editorState) => {
        this.setState({
            editorState,
        });
    };

    handleSubmit(event) {
        const author = this.state.author;
        const title = this.state.title;
        let htmlContent = draftToHtml(convertToRaw(this.state.editorState.getCurrentContent()));
        if (author.length <= 0 || title.length <= 0) {
            alert('You must give title. Please try again.');
        } else if (htmlContent.length > 15000) {
            alert('Your blogpost is too long! Try to keep it under 15 000 characters, including styling.');
        } else {
            this.makeNewPost();
        }

        event.preventDefault();
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location) {
            this.onRouteChanged();
        }
    }

    onRouteChanged() {
        this.setState({
            author: ''
            , title: ''
            , content: ''
            , tags: ''
            , modifying: false});
    }

    componentDidMount() {
        if(localStorage.getItem('userType') === 'ADMIN' && localStorage.getItem('loggedin') === 'true') {
            if (this.props.match.params.id === 'new') {
                this.setState({
                    formTitle: 'Make a new post'
                    , author: localStorage.getItem('username')
                    , modifying: false
                })
            } else if (isNaN(this.props.match.params.id)) {
                this.setState({
                    postFound: false
                    , errorMessage: 'Found no matching post'
                })
            } else {
                fetch('/api/blogposts/' + this.props.match.params.id)
                    .then((httpResponse) => httpResponse.json())
                    .then((post) => {
                        if (post) {
                            let tags = '';
                            for (let t of post.tags) {
                                tags += t.tagName;
                                tags += ',';
                            }
                            tags = tags.slice(0, -1);

                            const blocksFromHtml = htmlToDraft(post.content);
                            const {contentBlocks, entityMap} = blocksFromHtml;
                            const contentState = ContentState.createFromBlockArray(contentBlocks, entityMap);
                            const editorState = EditorState.createWithContent(contentState);
                            //console.log('postinfo: ', post)
                            this.setState({
                                formTitle: 'Modify a post'
                                , author: post.author
                                , title: post.title
                                , editorState: editorState
                                , tags: tags
                                , modifying: true
                            });
                        } else {
                            this.setState({postFound: false
                                , errorMessage: 'Found no matching post'});
                        }
                });
            }
        } else {
            this.setState({postFound: false
                , errorMessage: 'You\'re not authorized to modify posts.'});
        }
    }

    async makeNewPost() {
        let htmlContent = draftToHtml(convertToRaw(this.state.editorState.getCurrentContent()));

        var tagArray = this.state.tags.split(',');

        for (let i in tagArray) {
            let tag = tagArray[i];
            tagArray[i] = tag.trim();
        }

        let filtered = tagArray.filter(function (el) {
            return el !== '';
        });

        tagArray = filtered;

        if(this.state.modifying){
            const newPost = {
                title: this.state.title,
                content: htmlContent,
                tags: tagArray
            };
            await fetch('/api/blogposts/' + this.props.match.params.id, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newPost),
            }).finally(() => {
                this.props.history.push("/")
            })
        } else {
            const newPost = {
                authorId: localStorage.getItem("userId"),
                title: this.state.title,
                content: htmlContent,
            };
            await fetch('/api/blogposts/', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newPost),
            }).then((response) => {
                return response.json()
            }).then((value) => {
                //console.log(value);

                if (tagArray.length > 0) {
                    fetch('/api/blogposts/' + value + '/tag', {
                        method: 'POST',
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(tagArray),
                    })
                }
            }).finally(() => {
                this.props.history.push("/")
            })
        }
    }

    async deletePost() {
        await fetch('/api/blogposts/' + this.props.match.params.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            this.props.history.push('/');
        });
    }

    render() {
        const { editorState } = this.state;
        let content = draftToHtml(convertToRaw(this.state.editorState.getCurrentContent()));
        let contentLength = content.length;
        return (
            <div className = {"container"}>
                {this.state.postFound ?
                    <div>
                        <div className="newpostheader">
                            <h1 className={"newpostTitle"}>{this.state.formTitle}</h1>
                        </div>

                            <div className="required">
                                <label>
                                    Title
                                    <br />
                                    <input type="text" value={this.state.title} onChange={this.handleChange} name="title"/>
                                </label>
                            </div>

                            <br />

                            <label>
                                Content
                                <br />
                                <div>
                                    <Editor
                                        editorState={editorState}
                                        toolbarClassName="toolbarClassName"
                                        wrapperClassName="wrapperClassName"
                                        editorClassName="editorClassName"
                                        onEditorStateChange={this.onEditorStateChange}
                                    />
                                </div>
                            </label>

                            <p className={contentLength > this.state.maxContentLength ? "tooLong" : "normal"}>
                                Content length (incl. styling) {contentLength}/{this.state.maxContentLength}
                            </p>
                            <p>
                                <label>
                                   Tags
                                   <br />
                                   <input type="text" value={this.state.tags} onChange={this.handleChange} name="tags"/>
                                </label>
                            </p>
                            <div className={"row"}>
                                <button className="submit-button app-button" onClick={this.handleSubmit}>Submit</button>
                                <button className="cancel-button app-button" onClick={() => this.props.history.push('/')}>Cancel</button>
                                {this.state.modifying &&
                                    <button className="delete-button app-button" onClick={this.deletePost}>Delete post</button>
                                }
                            </div>

                    </div>
                    :
                    <ErrorPage message={this.state.errorMessage}/>
                }
            </div>
        );
    }
}

export default withRouter(NewPostForm);

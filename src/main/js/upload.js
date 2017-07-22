import React, {Component} from "react";
import RaisedButton from "material-ui/RaisedButton";
import getMuiTheme from "material-ui/styles/getMuiTheme";
import MuiThemeProvider from "material-ui/styles/MuiThemeProvider";
import {deepPurple400, tealA700} from "material-ui/styles/colors";
import Dropzone from "react-dropzone";
const client = require('./client');
let rest = require('rest');


class JsonUploadButton extends Component {
    constructor(props) {
        super(props);
        this.uploadFiles = this.uploadFiles.bind(this);
        this.state = {
            files: []
        }
    }


    uploadFiles() {
        let formData = new FormData();
        this.state.files.forEach(function (file) {
            formData.append("filesToUpload", file)
        });
        rest({
            method: 'POST',
            path: '/api/upload',
            headers: 'multipart/form-data',
            entity: formData,
        })
            .done(response => {
                console.log(response)
            });

    }

    onDrop(acceptedFiles, rejectedFiles) {
        // do stuff with files...
        let files = this.state.files;
        let fileRender = this.state.fileRender;
        files.push(acceptedFiles);
        this.setState({files: files})
    }

    uploadedFile() {
        return <aside>
            <h4>Files ready for upload:</h4>
            <ul>
                {
                    this.state.files.map(f => <li>{f[0].name}</li>)
                }
            </ul>
        </aside>
    }

    uploadButton() {
        if (this.state.files.length > 0) {
            return <MuiThemeProvider muiTheme={getMuiTheme(
                {
                    palette: {
                        primary1Color: deepPurple400,
                        secondary1Color: tealA700,
                    }
                }
            )}>
                <div>
                    <br />
                    <RaisedButton label="Upload" primary={true} fullWidth={true} containerElement='label' onClick={this.uploadFiles}>
                    </RaisedButton>
                </div>
            </MuiThemeProvider>
        }
    }

    render() {
        return <section>
            <div className="dropzone">
                <Dropzone onDrop={this.onDrop.bind(this)}>
                    <p>Drop Files Here or Click To Upload a json file.</p>
                </Dropzone>
                {this.uploadedFile()}
            </div>
            {this.uploadButton()}
        </section>

    }
}
export default JsonUploadButton;
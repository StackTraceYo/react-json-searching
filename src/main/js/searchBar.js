import React, {Component} from "react";
import {AutoComplete} from "material-ui";
import getMuiTheme from "material-ui/styles/getMuiTheme";
import MuiThemeProvider from "material-ui/styles/MuiThemeProvider";
import injectTapEventPlugin from "react-tap-event-plugin";
import {RadioButton, RadioButtonGroup} from "material-ui/RadioButton";
import {deepPurple400} from "material-ui/styles/colors";
const client = require('./client');

injectTapEventPlugin();

class SearchBarWithAutoComplete extends Component {
    constructor(props) {
        super(props);
        this.onUpdateInput = this.onUpdateInput.bind(this);
        this.onNewRequest = this.onNewRequest.bind(this);
        this.getAvailableFiles = this.getAvailableFiles.bind(this);
        this.updateSelectedFile = this.updateSelectedFile.bind(this);
        this.state = {
            dataSource: [],
            inputValue: '',
            jsonPath: '',
            files: [],
            selectedFile: ''
        };
        this.getAvailableFiles()
    }

    getAvailableFiles() {

        const self = this;

        client({
            method: 'GET',
            path: '/api/files',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .done(response => {
                this.setState({
                    files: response.entity.files,
                    selectedFile: response.entity.files ? response.entity.files[0] : ""
                })
            });

    }

    performSearch() {
        const self = this;

        client({
            method: 'POST',
            path: '/api/search',
            entity: {jsonFile: this.state.selectedFile, input: self.state.inputValue, reverse: this.props.reverse},
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .done(response => {
                this.state.jsonPath = response.entity.jsonPath;
                console.log("Calling Callback")
                self.props.callback(response.entity);
            });

    }

    performSuggest() {
        if (this.props.reverse) {
            console.log("suggest disabled for reverse lookup");
        } else {
            client({
                method: 'POST',
                path: '/api/suggest',
                entity: {jsonFile: this.state.selectedFile, input: this.state.inputValue},
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                }
            })
                .done(response => {
                    this.setState({
                        dataSource: response.entity.keys,
                        jsonPath: response.entity.jsonPath
                    });
                });
        }
    }

    onUpdateInput(inputValue) {
        const self = this;
        self.setState({
            inputValue: inputValue
        }, function () {
            self.performSuggest();
        });
    }

    onNewRequest(searchTerm) {
        const self = this;
        this.refs['autocomplete'].focus();
        this.performSearch();
    }

    updateSelectedFile(object, value) {
        const self = this;
        self.setState(
            {
                selectedFile: value
            }
        )
    }

    createRadioButtons() {
        const styles = {
            radioButton: {
                float: "right",
                marginLeft: 50
            }
        };
        return <div id="fileText">
            Files Available
            <MuiThemeProvider muiTheme={getMuiTheme(
                {
                    palette: {
                        primary1Color: deepPurple400,
                    }
                }
            )}>
                <RadioButtonGroup
                    name="files"
                    onChange={this.updateSelectedFile}
                    defaultSelected="translation"
                    style={styles.radioButton}
                >
                    {
                        this.state.files.map(file => <RadioButton
                            value={file}
                            label={file}
                        />)
                    }
                </RadioButtonGroup>
            </MuiThemeProvider>
        </div>
    }

    render() {
        const styles = {
            searchBar: {
                flat: "left"
            }
        };
        if (this.props.reverse) {
            this.state.dataSource = [];
        }
        return (
            <div>
                <div id="radio">
                    {this.createRadioButtons()}
                </div>
                <MuiThemeProvider muiTheme={getMuiTheme(
                    {
                        palette: {
                            primary1Color: deepPurple400,
                        }
                    }
                )}>
                    <AutoComplete
                        ref={`autocomplete`}
                        searchText={this.state.inputValue}
                        floatingLabelText={this.props.placeHolder}
                        filter={AutoComplete.noFilter}
                        openOnFocus={true}
                        fullWidth={true}
                        dataSource={this.state.dataSource}
                        onUpdateInput={this.onUpdateInput}
                        onNewRequest={this.onNewRequest}
                        style={styles.searchBar}
                    />
                </MuiThemeProvider>

            </div>
        )


    }
}
export default SearchBarWithAutoComplete;
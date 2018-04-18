import React, {Component} from 'react';
import './App.css';
import {Button, Col, ControlLabel, FormControl, Grid, Modal, Row, Table} from 'react-bootstrap';

const Months = Object.freeze({
                                 Jan: 0,
                                 Feb: 1,
                                 Mar: 2,
                                 Apr: 3,
                                 May: 4,
                                 Jun: 5,
                                 Jul: 6,
                                 Aug: 7,
                                 Sep: 8,
                                 Oct: 9,
                                 Nov: 10,
                                 Dec: 11
                             });

const dateTimeFormatter = Intl.DateTimeFormat('en-GB', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
});

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            currentMonth: Months.Apr,
            indicators: [
                {
                    name: 'Текущая страница книги Джедайские техники',
                    history: Array(30).fill(0),
                },
            ]
        };
    }

    render() {
        return (
            <Grid>
                <Row className="show-grid">
                    <Col xs={12} md={8}>
                        <IndicatorsList indicators={this.state.indicators} currentMonth={this.state.currentMonth}/>
                    </Col>
                </Row>
            </Grid>
        );
    }
}

class IndicatorsList extends Component {
    render() {
        const indicators = this.props.indicators.map(
            indicator => <Indicator history={indicator.history}
                                    key={indicator.name}
                                    name={indicator.name}
            />);
        return (
            <div>
                <h1>Indicators</h1>
                <Table striped bordered condensed hover>
                    <MonthHeader month={this.props.currentMonth} name='Indicators'/>
                    <tbody>
                    {indicators}
                    </tbody>
                </Table>
            </div>
        );
    }
}

function MonthHeader(props) {
    const daysInMonth = new Date(2018, props.month, 0).getDate();

    var dates = [];
    for (var i = 1; i < daysInMonth; i++) {
        dates.push(<th key={i} className="rotate">
            <div>{dateTimeFormatter.format(new Date(2018, props.month, i))}</div>
        </th>);
    }
    return (
        <thead>
        <tr>
            <th>{props.name}</th>
            {dates}
        </tr>
        </thead>
    );
}

class Indicator extends Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            history: props.history,
        };
    }

    handleValueChange(newValue, day) {
        // console.log('set new value= ' + newValue + ' for day ' + day);
        let valuesForCurrentMonth = this.state.history;
        valuesForCurrentMonth[day] = newValue;
        this.setState({history: valuesForCurrentMonth});
    }

    render() {
        let ths = this.state.history.map((value, day) => <IndicatorValue day={day}
                                                                         key={day}
                                                                         indicatorName={this.props.name}
                                                                         currentValue={value}
                                                                         onValueChange={(newValue, day) => this.handleValueChange(newValue, day)}/>);
        return (
            <tr>
                <td>
                    {this.props.name}
                </td>
                {ths}
            </tr>
        );
    }
}

class IndicatorValue extends Component {
    constructor(props, context) {
        super(props, context);

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.state = {
            show: false,
            value: this.props.currentValue,
        };
    }

    handleShow() {
        console.log("in show");
        this.setState({show: true});
    }

    handleClose() {
        this.setState({show: false});
    }

    handleChange(e) {
        this.setState({value: e.target.value});
    }

    handleSubmit() {
        this.props.onValueChange(Number.parseInt(this.state.value, 10), this.props.day);
        this.handleClose();
    }

    render() {
        return (<td>
            <p onClick={this.handleShow}>{this.props.currentValue === 0 ? "-": this.props.currentValue}</p>
            <Modal show={this.state.show} onHide={this.handleClose}>
                <Modal.Header>
                    {this.props.indicatorName}
                </Modal.Header>
                <Modal.Body>
                    <ControlLabel>Введите  новое значение для {dateTimeFormatter.format(new Date(2018, 3, this.props.day))}</ControlLabel>
                    <FormControl
                        autoFocus
                        type="text"
                        value={this.state.value}
                        placeholder="Enter text"
                        onChange={this.handleChange}
                        onKeyPress={(event) => {
                            if(event.key === 'Enter'){this.handleSubmit();}}}
                    />
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.handleSubmit} bsStyle="primary">Сохранить</Button>
                    <Button onClick={this.handleClose}>Отмена</Button>
                </Modal.Footer>
            </Modal>
        </td>);
    }
}

export default App;

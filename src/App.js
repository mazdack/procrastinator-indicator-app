import React, {Component} from 'react';
import './App.css';
import {Button, Col, ControlLabel, FormControl, Grid, Modal, Row, Table} from 'react-bootstrap';
import ButtonToolbar from "react-bootstrap/es/ButtonToolbar";

const Months = Object.freeze({
                                 Jan: 1,
                                 Feb: 2,
                                 Mar: 3,
                                 Apr: 4,
                                 May: 5,
                                 Jun: 6,
                                 Jul: 7,
                                 Aug: 8,
                                 Sep: 9,
                                 Oct: 10,
                                 Nov: 11,
                                 Dec: 12
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
            currentMonth: Months.May,
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
    constructor(props) {
        super(props);
        this.state = {
            showCreateIndicator: false,
            indicators: props.indicators,
        };

        this.handleShowCreateIndicator = this.handleShowCreateIndicator.bind(this);
        this.handleCloseCreateIndicator = this.handleCloseCreateIndicator.bind(this);
        this.handleAddIndicator = this.handleAddIndicator.bind(this);
    }

    handleCloseCreateIndicator() {
        this.setState({ showCreateIndicator: false });
    }

    handleShowCreateIndicator() {
        this.setState({ showCreateIndicator: true });
    }

    handleAddIndicator() {
        let indicators = this.state.indicators;
        indicators.push({name: this.state.newIndicatorName, history: []});

        this.setState({
            indicators: indicators,
                      });

        this.handleCloseCreateIndicator();
    }

    render() {
        const indicators = this.state.indicators.map(
            indicator => <Indicator history={indicator.history}
                                    key={indicator.name}
                                    name={indicator.name}
                                    month={this.props.currentMonth}
            />);
        return (
            <div>
                <h1>Indicators</h1>
                <ButtonToolbar>
                    <Button onClick={this.handleShowCreateIndicator}>Добавить индикатор</Button>
                </ButtonToolbar>
                <Table striped bordered condensed hover>
                    <MonthHeader month={this.props.currentMonth} name='Indicators'/>
                    <tbody>
                    {indicators}
                    </tbody>
                </Table>
                <Modal show={this.state.showCreateIndicator} onHide={this.handleCloseCreateIndicator}>
                    <Modal.Header>
                        Создание нового индикатора
                    </Modal.Header>
                    <Modal.Body>
                        <ControlLabel>Введите название индикатора</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.value}
                            placeholder="Enter text"
                            onChange={(e) => this.setState({newIndicatorName: e.target.value})}
                            onKeyPress={(event) => {
                                if(event.key === 'Enter'){this.handleAddIndicator();}}}
                        />
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this.handleAddIndicator} bsStyle="primary">Сохранить</Button>
                        <Button onClick={this.handleCloseCreateIndicator}>Отмена</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }
}

function MonthHeader(props) {
    let date = new Date(2018, props.month, 0);
    const daysInMonth = date.getDate();
    // console.log(date + ' ' + date.getDate());
    // console.log('days in month ' + props.month + ' are ' + daysInMonth);
    var dates = [];
    for (var i = 1; i <= daysInMonth; i++) {
        // console.log(i);
        dates.push(<th key={i} className="rotate">
            <div>{dateTimeFormatter.format(new Date(2018, props.month-1, i))}</div>
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
        const daysInMonth = new Date(2018, this.props.month, 0).getDate();
        console.log(this.props.name + ' ' + daysInMonth + ' ' + this.props.month);
        let ths = Array(daysInMonth);
        for (var index = 0; index < daysInMonth; index++) {
            let value = index >= this.state.history.length? 0: this.state.history[index];
            ths.push(<IndicatorValue day={index}
                                     key={index}
                                     indicatorName={this.props.name}
                                     currentValue={value}
                                     onValueChange={(newValue, day) => this.handleValueChange(newValue, day)}/>);
        }
        // let ths = this.state.history.map((value, day) => <IndicatorValue day={day}
        //                                                                  key={day}
        //                                                                  indicatorName={this.props.name}
        //                                                                  currentValue={value}
        //                                                                  onValueChange={(newValue, day) => this.handleValueChange(newValue, day)}/>);
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
            <p onClick={this.handleShow}>{(this.props.currentValue === 0 ||  !this.props.currentValue)? "-": this.props.currentValue}</p>
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

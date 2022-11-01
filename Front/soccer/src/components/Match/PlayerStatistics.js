import React from "react";
import PropTypes from "prop-types";
import Box from "@mui/material/Box";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableSortLabel from "@mui/material/TableSortLabel";
import Paper from "@mui/material/Paper";
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { Input, InputLabel, Button } from "@mui/material";
import { visuallyHidden } from "@mui/utils";
import axios from "axios";
import { useParams } from "react-router";
import { useSelector } from "react-redux";

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function getComparator(order, orderBy) {
  return order === "desc"
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function stableSort(array, comparator) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) {
      return order;
    }
    return a[1] - b[1];
  });
  return stabilizedThis.map((el) => el[0]);
}

const headCells = [
  {
    id: "name",
    numeric: false,
    disablePadding: true,
    label: "이름",
  },
  {
    id: "goal",
    numeric: true,
    disablePadding: false,
    label: "골",
  },
  {
    id: "assist",
    numeric: true,
    disablePadding: false,
    label: "도움",
  },
  {
    id: "shot",
    numeric: true,
    disablePadding: false,
    label: "슈팅",
  },
  {
    id: "shotOn",
    numeric: true,
    disablePadding: false,
    label: "유효슈팅",
  },
  {
    id: "pass",
    numeric: true,
    disablePadding: false,
    label: "패스",
  },
  {
    id: "passOn",
    numeric: true,
    disablePadding: false,
    label: "패스성공",
  },
  {
    id: "dribble",
    numeric: true,
    disablePadding: false,
    label: "드리블",
  },
  {
    id: "dribbleOn",
    numeric: true,
    disablePadding: false,
    label: "드리블성공",
  },
  {
    id: "crossedOn",
    numeric: true,
    disablePadding: false,
    label: "크로스성공",
  },
  {
    id: "tackle",
    numeric: true,
    disablePadding: false,
    label: "태클",
  },
  {
    id: "foul",
    numeric: true,
    disablePadding: false,
    label: "파울",
  },
  {
    id: "clear",
    numeric: true,
    disablePadding: false,
    label: "걷어내기",
  },
  {
    id: "yellow",
    numeric: true,
    disablePadding: false,
    label: "경고",
  },
  {
    id: "red",
    numeric: true,
    disablePadding: false,
    label: "퇴장",
  },
];

function EnhancedTableHead(props) {
  const { order, orderBy, onRequestSort } = props;
  const createSortHandler = (property) => (event) => {
    onRequestSort(event, property);
  };

  return (
    <TableHead>
      <TableRow>
        {headCells.map((headCell) => (
          <TableCell
            key={headCell.id}
            align={headCell.numeric ? "right" : "left"}
            padding={headCell.disablePadding ? "none" : "normal"}
            sortDirection={orderBy === headCell.id ? order : false}
          >
            <TableSortLabel
              active={orderBy === headCell.id}
              direction={orderBy === headCell.id ? order : "asc"}
              onClick={createSortHandler(headCell.id)}
            >
              {headCell.label}
              {orderBy === headCell.id ? (
                <Box component="span" sx={visuallyHidden}>
                  {order === "desc" ? "sorted descending" : "sorted ascending"}
                </Box>
              ) : null}
            </TableSortLabel>
          </TableCell>
        ))}
      </TableRow>
    </TableHead>
  );
}

EnhancedTableHead.propTypes = {
  onRequestSort: PropTypes.func.isRequired,
  order: PropTypes.oneOf(["asc", "desc"]).isRequired,
  orderBy: PropTypes.string.isRequired,
  rowCount: PropTypes.number.isRequired,
};

const PlayerStatistics = (props) => {

  const match = useSelector((state)=>state.match);
  const [order, setOrder] = React.useState("asc");
  const [orderBy, setOrderBy] = React.useState("calories");
  const [selected, setSelected] = React.useState('');
  const [score, setScore] = React.useState(-1);

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === "asc";
    setOrder(isAsc ? "desc" : "asc");
    setOrderBy(property);
  };

  const handleChange = (event) => {
    setSelected(event.target.value);
  }

  const handleScore = (event) => {
    setScore(event.target.value);
  }

  const submit = () => {
    if(selected === ''){
      alert('선수를 선택하세요');
    }
    else if(Number(score) < 0 || Number(score) > 10){
      alert('0.0 ~ 10.0 사이의 숫자를 입력하세요');
    }
    else{
      axios.post(`http://localhost:8080/api/v1/matches/predict/player`,{
        matchId: match.matchId,
        userEmail: 'test@test.com',
        playerId: selected,
        score: Number(score),
      })
      .then(()=>{
        setScore(-1);
        setSelected('');
        alert('평가가 완료되었습니다');
      })
    }
  }

  return (
    <Box sx={{ width: "100%" }}>
      <Paper sx={{ width: "100%", mb: 2 }}>
        <TableContainer>
          <Table sx={{ minWidth: 750 }} aria-labelledby="tableTitle">
            <EnhancedTableHead
              order={order}
              orderBy={orderBy}
              onRequestSort={handleRequestSort}
              rowCount={props.team.length}
            />
            <TableBody>
              {/* if you don't need to support IE11, you can replace the `stableSort` call with:
                 rows.sort(getComparator(order, orderBy)).slice() */}
              {stableSort(props.team, getComparator(order, orderBy)).map(
                (row, index) => {
                  const labelId = `enhanced-table-checkbox-${index}`;
                  return (
                    <TableRow hover role="checkbox" tabIndex={-1} key={index}>
                      <TableCell
                        component="th"
                        id={labelId}
                        scope="row"
                        padding="none"
                      >
                        {row.playerName}
                      </TableCell>
                      <TableCell align="right">{row.goal}</TableCell>
                      <TableCell align="right">{row.assist}</TableCell>
                      <TableCell align="right">{row.shot}</TableCell>
                      <TableCell align="right">{row.shotOn}</TableCell>
                      <TableCell align="right">{row.pass}</TableCell>
                      <TableCell align="right">{row.passOn}</TableCell>
                      <TableCell align="right">{row.dribble}</TableCell>
                      <TableCell align="right">{row.dribbleOn}</TableCell>
                      <TableCell align="right">{row.crossedOn}</TableCell>
                      <TableCell align="right">{row.tackle}</TableCell>
                      <TableCell align="right">{row.foul}</TableCell>
                      <TableCell align="right">{row.clear}</TableCell>
                      <TableCell align="right">{row.yellow}</TableCell>
                      <TableCell align="right">{row.red}</TableCell>
                    </TableRow>
                  );
                }
              )}
            </TableBody>
          </Table>
        </TableContainer>
        <Box sx={{ minWidth: 120 }}>
          <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">선수</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              value={selected}
              label="선수"
              onChange={handleChange}
            >
              {props.team.map((player,index) => (
                <MenuItem value={player.player_id} key={index}>{player.playerName}</MenuItem>
              ))}
            </Select>
          </FormControl>
          <FormControl>
            <InputLabel htmlFor="score">점수(0.0 ~ 10.0)</InputLabel>
            <Input id="score" type="number" required onChange={handleScore}/>
            <Button onClick={submit}>평가하기</Button>
          </FormControl>
        </Box>
      </Paper>
    </Box>
  );
};

export default PlayerStatistics;

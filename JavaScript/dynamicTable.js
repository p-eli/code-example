/* 
 * File: dynamicTable.js
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Project: Tabulka s dynamickými sloupci a řádky
 */


/*
 * Global variable with table ID's
 */
var tableIDs = {};


/*
 * Function automatically inicializes all table which are assigned IDs.
 * Function:  
 *  autoInit
 */
function autoInitDynamicTables() {
    var tables = document.getElementsByTagName("table");
    for (var x = 0; x < tables.length; x++) {
        if (tables[x].id) {
            initDynamicTable(tables[x].id);
        }
    }
}


/*
 * Function inicialize table witch set id.
 * Function: 
 *  initScript
 * Parametrs:  
 *  id - table id
 */
function initDynamicTable(id) {
    if (document.getElementById(id) !== null) {
        tableIDs[id] = {'swap': null, 'resize': null};
        document.onmousemove = function (e) {
            mouseMove(e);
        };
        document.onmouseup = function (e) {
            mouseUp();
        };
        initTableMouseListener(id);
    } else {
        window.alert("Table \"" + id + "\" not found.");
    }
}


/*
 * Function init table mouse listener.
 * Function: 
 *  initTableMouseListener
 * Parametrs:  
 *  id - table id
 */
function initTableMouseListener(id) {
    var table = document.getElementById(id);
    table.style.width = "auto";
    table.style.height = "auto";
    //init table onmouse leave action
    table.onmouseleave = function (e, id) {
        if (e.which === 1) {
            for (var x in tableIDs) {
                if (tableIDs[x]['swap'] !== null) {
                    tableIDs[x]['swap'] = null;
                }
            }
        }
    };
    //init table cells on mouse down listener
    var table_rows = table.rows;
    for (var y = 0; y < table_rows.length; y++) {
        for (var x = 0; x < table_rows[y].cells.length; x++) {
            table_rows[y].cells[x].onmousedown = function (e) {
                var border = 5;
                if (e.target.style.cursor === "default") { //no action
                    return;
                } else {
                    if (e.target.style.cursor === "nw-resize") { //table top left corner
                        tableIDs[id]['resize'] = {'function': tableTopLeftResize, 'elem': null};
                    } else if (e.target.style.cursor === "se-resize") { //table bottom right corner
                        tableIDs[id]['resize'] = {'function': tableBottomRightResize, 'elem': null};
                    } else if (e.target.style.cursor === "sw-resize") { //table bottom left corner
                        tableIDs[id]['resize'] = {'function': tableBottomLeftResize, 'elem': null};
                    } else if (e.target.style.cursor === "ne-resize") { //table top right corner
                        tableIDs[id]['resize'] = {'function': tableTopRightResize, 'elem': null};
                    } else if (e.target.style.cursor === "col-resize") { //table column resize
                        if ((e.offsetX < border)) { //left border
                            var row = getTableRowAndColumn(0, 0, getPositionInTable(e.target, id)['posY'], e.target.colSpan, id)['rows'];
                            for (var x = 0; x < row.length; x++) {
                                if (row[x] === e.target) {
                                    if (x > 0) { // resize left column
                                        tableIDs[id]['resize'] = {'function': tableColResize, 'elem': row[x - 1]};
                                    } else { //table border, resize right column
                                        tableIDs[id]['resize'] = {'function': tableLeftColResize, 'elem': e.target};
                                    }
                                }
                            }
                        } else { //right border resize left column
                            tableIDs[id]['resize'] = {'function': tableColResize, 'elem': e.target};
                        }
                    } else if (e.target.style.cursor === "row-resize") { //table row resize
                        if ((e.offsetY < border)) { //top border
                            var column = getTableRowAndColumn(getPositionInTable(e.target, id)['posX'], e.target.colSpan, 0, 0, id)['column'];
                            for (var i = 0; i < column.length; i++) {
                                for (var j = 0; j < column[i].length; j++) {
                                    if (column[i][j] === e.target) {
                                        if (i > 0) { //search row over  border
                                            for (var z = i - 1; z >= 0; z--) {
                                                if (column[z].length > 0) { //resize row over  border
                                                    tableIDs[id]['resize'] = {'function': tableRowResize, 'elem': column[z][0]};
                                                    return;
                                                } else { // resize row under border 
                                                    tableIDs[id]['resize'] = {'function': tableTopRowResize, 'elem': e.target};
                                                }
                                            }
                                        } else { // resize row under borde
                                            tableIDs[id]['resize'] = {'function': tableTopRowResize, 'elem': e.target};
                                        }
                                        return;
                                    }
                                }
                            }
                        } else { // bottom border, resize row over border
                            tableIDs[id]['resize'] = {'function': tableRowResize, 'elem': e.target};
                        }
                    } else if (e.target.style.cursor === "move") { // move column
                        tableIDs[id]['swap'] = e.target;
                    }
                }
            };
            //init table cells on mouse up listener
            table_rows[y].cells[x].onmouseup = function (e) {
                var border = 10;
                if (tableIDs[id]['swap'] !== null) {
                    var tr = document.getElementById(id).rows[0].getElementsByTagName("th");
                    for (var z = 0; z < tr.length; z++) {
                        if ((e.target === tr[z]) && (tableIDs[id]['swap'] !== e.target)) {
                            if ((e.offsetX < border)) {
                                headElementMoveBefore(e.target, id);
                            } else if (e.offsetX > e.target.offsetWidth - border) {
                                headElementMoveAfter(e.target, id);
                            } else {
                                headElementSwap(e.target, id);
                            }

                        }
                    }
                }
                mouseUp();
            };
            //init table cells on mouse move listener
            table_rows[y].cells[x].onmousemove = function (e) {
                var border = 5;
                if (e.buttons === 0) {
                    var el_width = e.target.offsetWidth;
                    var el_heigth = e.target.offsetHeight;
                    var table_rows = document.getElementById(id).rows;
                    if ((e.offsetX < border)
                            && (e.offsetY < border)
                            && (table_rows[0].cells[0] === e.target)) {//left top corner
                        e.target.style.cursor = "nw-resize";
                    } else if ((e.offsetX > el_width - border)
                            && (e.offsetY > el_heigth - border)
                            && (table_rows[table_rows.length - 1].cells[table_rows[table_rows.length - 1].cells.length - 1] === e.target)) { // right bottom corner
                        e.target.style.cursor = "se-resize";
                    } else if ((e.offsetX < border)
                            && (e.offsetY > el_heigth - border)
                            && (table_rows[table_rows.length - 1].cells[0] === e.target)) { //left bottom corner
                        e.target.style.cursor = "sw-resize";
                    } else if ((e.offsetX > el_width - border)
                            && (e.offsetY < border)
                            && (table_rows[0].cells[table_rows[0].cells.length - 1] === e.target)) { //right top corner
                        e.target.style.cursor = "ne-resize";
                    } else if ((e.offsetX < border)
                            || (e.offsetX > el_width - border)) { //left or right border
                        e.target.style.cursor = "col-resize";
                    } else if ((e.offsetY < border)
                            || (e.offsetY > el_heigth - border)) { //top or bottom border
                        e.target.style.cursor = "row-resize";
                    } else { // test if cells are movable 
                        var tr = document.getElementById(id).getElementsByTagName("tr")[0].getElementsByTagName("th");
                        for (var z = 0; z < tr.length; z++) {
                            if (e.target === tr[z]) {
                                e.target.style.cursor = "move";
                                return;
                            }
                        }
                        e.target.style.cursor = "default";
                    }
                } else if (e.buttons === 1) { // right mouse button prest
                    var tr = document.getElementById(id).getElementsByTagName("tr")[0].getElementsByTagName("th");
                    for (var z = 0; z < tr.length; z++) { //show move culsor
                        if (e.target === tr[z]) {
                            if ((e.offsetX < border)) {
                                e.target.style.cursor = "w-resize";
                            } else if (e.offsetX > e.target.offsetWidth - border) {
                                e.target.style.cursor = "e-resize";
                            } else {
                                e.target.style.cursor = "move";
                            }
                            return;
                        }
                    }
                }
            };
        }
    }
}


/*
 * Function implement mouse move action.
 * If right button is prest, then call resize function.
 * Function: 
 *  mouseMove
 * Prametrs:
 *  e - mouse event
 */
function mouseMove(e) {
    if (e.buttons === 1) {
        for (var id in tableIDs) {
            if (tableIDs[id]['resize'] !== null) {
                tableIDs[id]['resize']['function'](e, id);
                break;
            }
        }
    }
}

/*
 * Function implement mouse up action.
 * Function clear all mouse douwn action.
 * Function: 
 *  mouseUp
 */
function mouseUp() {
    for (var id in tableIDs) {
        tableIDs[id]['resize'] = null;
        tableIDs[id]['swap'] = null;
    }
}


/*
 * Function calculate new size of table column, and call resize table function.
 * Calculate size for left border of column resize.
 * Function: 
 *  tableLeftColResize
 * Prametrs:
 *  e - mouse event
 *  id - resize table id
 */
function tableLeftColResize(e, id) {
    resizeCol((tableIDs[id]['resize']['elem'].getBoundingClientRect().left - e.pageX), id);
}


/*
 * Function calculate new size of table column, and call resize table function.
 * Calculate size for right border column resize.
 * Function:  
 *  tableColResize
 * Prametrs:
 *  e - mouse event
 *  id - resize table id
 */
function tableColResize(e, id) {
    resizeCol((e.pageX - tableIDs[id]['resize']['elem'].getBoundingClientRect().right), id);
}


/*
 * Function calculate new size of table row, and call resize table function.
 * Calculate size for bottom border row resize.
 * Function: 
 *  tableRowResize
 * Prametrs:
 *  e - mouse event
 *  id - resize table id
 */
function tableRowResize(e, id) {
    resizeRow((e.pageY - tableIDs[id]['resize']['elem'].getBoundingClientRect().bottom), id);
}


/*
 * Function calculate new size of table row, and call resize table function.
 * Calculate size for top border row resize.
 * Function: 
 *  tableTopRowResize
 * Prametrs:
 *  e - mouse event
 *  id - resize table id
 */
function tableTopRowResize(e, id) {
    resizeRow((tableIDs[id]['resize']['elem'].getBoundingClientRect().top - e.pageY), id);
}


/*
 * Function calculate new size of table, and call resize table function.
 * Calculate for top left corner.
 * Function: 
 *  tableTopLeftResize
 * Prametrs:
 *  e - mouse event
 *  id - resize table id
 */
function tableTopLeftResize(e, id) {
    var table = document.getElementById(id);
    resizeTable((table.getBoundingClientRect().left - e.pageX), (table.getBoundingClientRect().top - e.pageY), table);
}


/*
 * Function calculate new size of table, and call resize table function.
 * Calculate for top right corner.
 * Function: 
 *  tableTopRightResize
 * Prametrs:
 *  e - mouse event
 *  id - resize table id
 */
function tableTopRightResize(e, id) {
    var table = document.getElementById(id);
    resizeTable((e.pageX - table.getBoundingClientRect().right), (table.getBoundingClientRect().top - e.pageY), table);
}


/*
 * Function calculate new size of table, and call resize table function.
 * Calculate for bottom left corner.
 * Function: 
 *  tableBottomLeftResize
 * Prametrs:
 *  e - mouse event
 *  id - resize table id
 */
function tableBottomLeftResize(e, id) {
    var table = document.getElementById(id);
    resizeTable((table.getBoundingClientRect().left - e.pageX), (e.pageY - table.getBoundingClientRect().bottom), table);
}


/*
 * Function calculate new size of table, and call resize table function.
 * Calculate for bottom right corner.
 * Function: 
 *  tableBottomRightResize
 * Prametrs:
 *  e - mouse event
 *  id - resize table id
 */
function tableBottomRightResize(e, id) {
    var table = document.getElementById(id);
    resizeTable((e.pageX - table.getBoundingClientRect().right), (e.pageY - table.getBoundingClientRect().bottom), table);
}



/*
 * Function return column and row on set position.
 * Function: 
 *  getTableRowAndColumn
 * Prametrs:
 *  pos_col - column position
 *  col_span - span of column
 *  pos_row - row position
 *  row_span - pan of row
 *  id - resize table id
 */
function getTableRowAndColumn(pos_col, col_span, pos_row, row_span, id) {
    var table_rows = document.getElementById(id).rows;
    var cells_span = {};
    var posX = 0;
    var column_cells = [];
    var row_cells = [];
    var row_element = [];
    for (var x = 0; x < table_rows.length; x++) {
        row_element = [];
        posX = 0;
        for (var y = 0; y <= table_rows[x].cells.length; y++) {
            while (posX in cells_span) { //calculate with elements colspan
                var tmp = cells_span[posX]['colspan'];
                if ((x >= pos_row) && (x < pos_row + row_span)
                        && (row_cells.indexOf(cells_span[posX]['elem']) === -1)) {
                    row_cells.push(cells_span[posX]['elem']);
                }
                if (cells_span[posX]['rowspan'] > 1) {
                    cells_span[posX]['rowspan']--;
                } else {
                    delete cells_span[posX];
                }
                posX += tmp;
            }
            if (y < table_rows[x].cells.length) {
                //add cells to row element list
                if ((x >= pos_row)
                        && (x < pos_row + row_span)
                        && (row_cells.indexOf(table_rows[x].cells[y]) === -1)) {
                    row_cells.push(table_rows[x].cells[y]);
                }
                //add cells to column element list
                if (((posX >= pos_col)
                        && (posX < (pos_col + col_span))) || ((posX < pos_col)
                        && (posX + table_rows[x].cells[y].colSpan > pos_col))) {
                    row_element.push(table_rows[x].cells[y]);
                }
                //add element witch collspan to colspan element list
                if (table_rows[x].cells[y].rowSpan > 1) {
                    cells_span[posX] = {'rowspan': (table_rows[x].cells[y].rowSpan - 1),
                        'colspan': table_rows[x].cells[y].colSpan,
                        'elem': table_rows[x].cells[y]};
                }
                posX += table_rows[x].cells[y].colSpan;
            }
        }
        column_cells.push(row_element);
    }
    return {'column': column_cells, 'rows': row_cells};
}


/*
 * Function return position of element in table.
 * Function: 
 *  getPositionInTable
 * Prametrs:
 *  element - element whose position is searching
 *  id - resize table id
 */
function getPositionInTable(elem, id) {
    var table_rows = document.getElementById(id).rows;
    var rows_span = {};
    var posX = 0;
    for (var x = 0; x < table_rows.length; x++) {
        posX = 0;
        for (var y = 0; y <= table_rows[x].cells.length; y++) {
            if (posX in rows_span) {
                var tmp_pos = rows_span[posX]['colSpan'];

                if (rows_span[posX]['count'] > 1) {
                    rows_span[posX]['count']--;
                } else {
                    delete rows_span[posX];
                }
                posX += tmp_pos;
            }
            if (y < table_rows[x].cells.length) {
                if (table_rows[x].cells[y] === elem) {
                    return({'posX': posX, 'posY': x});
                }
                if (table_rows[x].cells[y].rowSpan > 1) {
                    rows_span[posX] = {'colSpan': table_rows[x].cells[y].colSpan, 'count': (table_rows[x].cells[y].rowSpan - 1)};
                }
                posX += table_rows[x].cells[y].colSpan;
            }
        }
    }
}


/*
 * Function resize table column.
 * Function: 
 *  resizeCol
 * Prametrs:
 *  resize - adding size
 *  id - resize table id
 */
function resizeCol(resize, id) {
    var col_arry = getTableRowAndColumn(
            getPositionInTable(tableIDs[id]['resize']['elem'], id)['posX'],
            tableIDs[id]['resize']['elem'].colSpan, 0, 0, id)['column'];
    var width_arry = [];
    //save actual size of all cells and sub new size
    for (var x = 0; x < col_arry.length; x++) {
        var item_arry = [];
        for (var y = 0; y < col_arry[x].length; y++) {
            item_arry.push(col_arry[x][y].getBoundingClientRect().width
                    + ((resize / tableIDs[id]['resize']['elem'].colSpan) * col_arry[x][y].colSpan));
        }
        width_arry.push(item_arry);
        item_arry = [];
    }
    //set new cells size
    for (var x = 0; x < col_arry.length; x++) {
        for (var y = 0; y < col_arry[x].length; y++) {
            col_arry[x][y].style.width = width_arry[x][y] + 'px';
        }
    }
}


/*
 * Function resize table row.
 * Function: 
 *  row_arry
 * Prametrs:
 *  resize - adding size
 *  id - resize table id
 */
function resizeRow(resize, id) {
    var row_arry = getTableRowAndColumn(0, 0, getPositionInTable(tableIDs[id]['resize']['elem'], id)['posY'], tableIDs[id]['resize']['elem'].rowSpan, id)['rows'];
    var item_arry = [];
    //save actual size of all cells and sub new size
    for (var x = 0; x < row_arry.length; x++) {
        item_arry.push(row_arry[x].getBoundingClientRect().height + ((resize / tableIDs[id]['resize']['elem'].rowSpan) * row_arry[x].rowSpan));
    }
    //set new cells size
    for (var x = 0; x < row_arry.length; x++) {
        row_arry[x].style.height = item_arry[x] + 'px';
    }
}


/*
 * Function resize width and height of all cells in table.
 * Function: 
 *  resizeTable
 * Prametrs:
 *  sizeX - adding X size
 *  sizeY - addomg Y size
 *  id - resize table id
 */
function resizeTable(sizeX, sizeY, table) {
    var lenX = 0;
    var lenY = 0;
    var x = 0;
    //calculate rows length
    for (x = 0; x < table.rows[0].cells.length; x++) {
        lenX += table.rows[0].cells[x].colSpan;
    }
    //calculate column length
    for (x = 0; x < table.rows.length; x++) {
        lenY += table.rows[x].cells[0].rowSpan;
    }
    var cells_height = [];
    var cells_width = [];
    var table_rows = table.rows;
    var tmpW = [];
    var tmpH = [];
    //save actual size of all cells and sub new size
    for (var x = 0; x < table_rows.length; x++) {
        tmpW = [];
        tmpH = [];
        for (var y = 0; y < table_rows[x].cells.length; y++) {
            tmpW.push(table_rows[x].cells[y].getBoundingClientRect().width + ((sizeX / lenX) * table_rows[x].cells[y].colSpan));
            tmpH.push(table_rows[x].cells[y].getBoundingClientRect().height + ((sizeY / lenY) * table_rows[x].cells[y].rowSpan));
        }
        if (tmpW.length > 0) {
            cells_height.push(tmpH);
            cells_width.push(tmpW);
        }
    }
    //set new cells size
    var x;
    for (x = 0; x < table_rows.length; x++) {
        for (var y = 0; y < table_rows[x].cells.length; y++) {
            table_rows[x].cells[y].style.width = cells_width[x][y] + 'px';
            table_rows[x].cells[y].style.height = cells_height[x][y] + 'px';
        }
    }
}


/*
 * Function inicialize start swaping column.
 * Function: 
 *  headElementSwap
 * Prametrs:
 *  element - table header element
 *  id - table id
 */
function headElementSwap(element, id) {
    if (element.cellIndex < tableIDs[id]['swap'].cellIndex) {
        swapTableColumn(getPositionInTable(element, id)['posX'], element.colSpan, getPositionInTable(tableIDs[id]['swap'], id)['posX'], tableIDs[id]['swap'].colSpan, id);
    } else {
        swapTableColumn(getPositionInTable(tableIDs[id]['swap'], id)['posX'], tableIDs[id]['swap'].colSpan, getPositionInTable(element, id)['posX'], element.colSpan, id);
    }
}


/*
 * Function inicialize move column before another column.
 * Function: 
 *  headElementMoveBefore
 * Prametrs:
 *  element - table header element
 *  id - table id
 */
function headElementMoveBefore(element, id) {
    moveTableColumn(getPositionInTable(tableIDs[id]['swap'], id)['posX'], tableIDs[id]['swap'].colSpan, getPositionInTable(element, id)['posX'], element.colSpan, id);
}


/*
 * Function inicialize move column after another column.
 * Function: 
 *  headElementMoveAfter
 * Prametrs:
 *  element - table header element
 *  id - table id
 */
function headElementMoveAfter(element, id) {
    // move before and then switch them
    moveTableColumn(getPositionInTable(tableIDs[id]['swap'], id)['posX'], tableIDs[id]['swap'].colSpan, getPositionInTable(element, id)['posX'], element.colSpan, id);
    if (element.cellIndex < tableIDs[id]['swap'].cellIndex) {
        swapTableColumn(getPositionInTable(element, id)['posX'], element.colSpan, getPositionInTable(tableIDs[id]['swap'], id)['posX'], tableIDs[id]['swap'].colSpan, id);
    } else {
        swapTableColumn(getPositionInTable(tableIDs[id]['swap'], id)['posX'], tableIDs[id]['swap'].colSpan, getPositionInTable(element, id)['posX'], element.colSpan, id);
    }
}

/*
 * Function swap two column.
 * Function: 
 *  swapTableColumn
 * Prametrs:
 *  pos1 - position of header cells one
 *  span1 - rowSpan of header cells one
 *  pos2 - position of header cells two
 *  span2 - rowSpan of header cells two
 *  id - table id
 */
function swapTableColumn(pos1, span1, pos2, span2, id) {
    var col1 = getTableRowAndColumn(pos1, span1, 0, 0, id)['column'];
    var col2 = getTableRowAndColumn(pos2, span2, 0, 0, id)['column'];
    for (var x = col1.length - 1; x >= 0; x--) {
        if ((col2[x].length > 0) && (col1[x].length > 0)) { // both colums have cells
            var temp = document.createElement("div");
            col1[x][0].parentNode.insertBefore(temp, col1[x][0]);
            for (var y = 0; y < col1[x].length; y++) {
                col2[x][0].parentNode.insertBefore(col1[x][y], col2[x][0]);
            }
            for (var y = 0; y < col2[x].length; y++) {
                temp.parentNode.insertBefore(col2[x][y], temp);
            }
            temp.parentNode.removeChild(temp);
        } else if (col1[x].length > 0) { // only column one have cells
            var preEl = getElementAfterPosition(id, pos2, x);
            for (var y = 0; y < col1[x].length; y++) {
                col1[x][y].parentNode.insertBefore(col1[x][y], preEl);
            }
        } else if (col2[x].length > 0) { //only column two have cells
            var preEl = getElementAfterPosition(id, pos1, x);
            for (var y = 0; y < col2[x].length; y++) {
                col2[x][y].parentNode.insertBefore(col2[x][y], preEl);
            }
        }
    }
}


/*
 * Function move column before another column.
 * Function: 
 *  moveTableColumn
 * Prametrs:
 *  pos1 - position of header cells one
 *  span1 - rowSpan of header cells one
 *  pos2 - position of header cells two
 *  span2 - rowSpan of header cells two
 *  id - table id
 */
function moveTableColumn(pos1, span1, pos2, span2, id) {
    var col1 = getTableRowAndColumn(pos1, span1, 0, 0, id)['column'];
    var col2 = getTableRowAndColumn(pos2, span2, 0, 0, id)['column'];
    for (var x = col1.length - 1; x >= 0; x--) {
        if ((col2[x].length > 0) && (col1[x].length > 0)) { // both colums have cells
            for (var y = 0; y < col1[x].length; y++) {
                col2[x][0].parentNode.insertBefore(col1[x][y], col2[x][0]);
            }
        } else if (col1[x].length > 0) { // only column one have cells
            var preEl = getElementAfterPosition(id, pos2, x);
            for (var y = 0; y < col1[x].length; y++) {
                col1[x][y].parentNode.insertBefore(col1[x][y], preEl);
            }
        }
    }
}

/*
 * Function return element after position.
 * Function: 
 *  getElementAfterPosition
 * Prametrs:
 *  pX - position X
 *  pY - position Y
 *  id - table id
 */
function getElementAfterPosition(id, pX, pY) {
    var table_rows = document.getElementById(id).rows;
    var rows_span = {};
    var posX = 0;
    for (var x = 0; x < table_rows.length; x++) {
        posX = 0;
        for (var y = 0; y <= table_rows[x].cells.length; y++) {
            if (posX in rows_span) {
                var tmp_pos = rows_span[posX]['colSpan'];
                if (rows_span[posX]['count'] > 1) {
                    rows_span[posX]['count']--;
                } else {
                    delete rows_span[posX];
                }
                posX += tmp_pos;
            }
            if (y < table_rows[x].cells.length) {
                if (((pY) === x) && (posX >= pX)) {
                    return table_rows[x].cells[y];
                }
                if (table_rows[x].cells[y].rowSpan > 1) {
                    rows_span[posX] = {'colSpan': table_rows[x].cells[y].colSpan, 'count': (table_rows[x].cells[y].rowSpan - 1)};
                }
                posX += table_rows[x].cells[y].colSpan;
            }
        }
    }
    return null;
}
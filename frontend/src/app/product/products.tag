import numeral from 'numeral';

<app-products>
    <table>
        <thead>
            <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Preço</th>
                <th if={showActionsColumn}></th>
            </tr>
        </thead>
        <tbody>
            <tr each={opts.products}>
                <td>
                    <div class="title"><strong>Código</strong></div>
                    <div class="value">{ code }</div>
                </td>
                <td>
                    <div class="title"><strong>Nome</strong></div>
                    <div class="value">{ name }</div>
                </td>
                <td>
                    <div class="title"><strong>Preço</strong></div>
                    <div class="value">{ numeral(price).format('$ 0,0.00') }</div>
                </td>
                <td if={showActionsColumn}>
                    <div class="title"></div>
                    <div class="value buttons">
                        <button if={showUpdateButton} class="button button-outline" type="button" onclick={handleEditClick}>Editar</button>
                        <button if={showDeleteButton} class="button button-danger button-outline" onclick={handleDeleteClick} type="button">Remover</button>
                    </div>
                </td>
            </tr>
            <tr if={!opts.products || !opts.products.length}>
                <td colspan={showActionsColumn ? 4 : 3} class="empty-result">
                    Nenhum produto encontrado.
                </td>
            </tr>
        </tbody>
    </table>

    <style type="scss">

        :scope {
            > table {

                * {
                    box-sizing: border-box;
                }

                > tbody {

                    > tr {
                        > td {
                            .buttons {
                                width: 100%;
                                text-align: right;

                                > .button {
                                    height: 3rem;
                                    line-height: 3rem;
                                    padding: 0 10px;
                                }
                            }

                            &.empty-result {
                               text-align: center;
                            }

                            > title {
                                display: none;
                            }
                        }
                    }
                }


                @media (max-width: 50.0rem) {

                    > thead {
                        display: none;
                    }

                    > tbody {
                        > tr {

                            > td {
                                display: flex;
                                width: 100%;
                                border: none;

                                > .title {
                                    display: block;
                                    width: 30%;
                                    border: none;
                                    text-align: right;
                                    margin-right: 15px;
                                }

                                > .value {
                                    display: block;
                                    width: 70%;
                                    border: none;
                                }

                                + td > .value,
                                + td > .title {
                                    border-left: none;
                                }

                                &:last-child {
                                    border-bottom: 1px solid #ccc;
                                }

                                &:last-child,
                                &:first-child {
                                    padding: 1.2rem 1.5rem;
                                }

                                > .buttons {
                                    text-align: left;
                                }

                            }

                            &:last-child > td:last-child {
                                border-bottom: none;
                            }

                        }
                    }
                }
            }

        }


    </style>

    <script>
        this.showUpdateButton = false;
        this.showActionsColumn = false;
        this.showDeleteButton = false;

        this.handleEditClick = (e) => {
            opts.oneditclick(e.item);
        }

        this.handleDeleteClick = (e) => {
            opts.ondeleteclick(e.item);
        }

        this.numeral = numeral;

        this.on('mount', () => {
            const showUpdateButton = typeof opts.oneditclick === 'function';
            const showDeleteButton = typeof opts.ondeleteclick === 'function';
            this.update({
                showUpdateButton,
                showDeleteButton,
                showActionsColumn: showUpdateButton || showDeleteButton,
            });
        });

    </script>

</app-products>
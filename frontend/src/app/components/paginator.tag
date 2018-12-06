<app-paginator> 
    <div class="app-paginator">
        <div class="app-paginator-container">
            <button disabled={opts.page < 3} onclick={first} class="button button-clear">{"<<"}</button>
            <button disabled={opts.page === 1} onclick={previous} class="button button-clear">{"<"}</button>
            <button 
                class={parent.getButtonClasses(page)} 
                each={page in pages} 
                disabled={page === opts.page}
                onclick={this.parent.changePage}>
                {page}
            </button>
            <button disabled={opts.page >= totalPages} onclick={next} class="button button-clear">{">"}</button>
            <button disabled={opts.page >= (totalPages - 1)} onclick={last} class="button button-clear">{">>"}</button>
            <select name="size" onchange={changeSize}>
                <option selected={opts.size === 5} value={5}>5 itens por página</option>
                <option selected={opts.size === 10} value={10}>10 itens por página</option>
                <option selected={opts.size === 15} value={15}>15 itens por página</option>
                <option selected={opts.size === 30} value={30}>30 itens por página</option>
                <option selected={opts.size === 50} value={50}>50 itens por página</option>
            </select>
        </div>
    </div>

    <style>
        :scope .button {
            padding: 0 15px;
        }

        :scope > .app-paginator {
            width: 100%;
            display: flex;
            flex-direction: row-reverse;
        }

        @media (min-width: 40.0rem) { 
            :scope select {
                width: 180px;
            }
         }
    </style>

    <script>

        this.totalPages = 0;
        this.pages = [];

        this.getButtonClasses = page => {
            return {
                'button': true,
                'button-outline': opts.page === page, 
                'button-clear': opts.page !== page
            };
        };

        this.on('mount', () => {
            this.calculate();
        });

        this.first = (e) => {
            opts.onpaginationchange({page: 1});
        };

        this.last = (e) => {
            opts.onpaginationchange({page: this.totalPages});
        };

        this.previous = (e) => {
            opts.onpaginationchange({page: opts.page - 1});
        };

        this.next = (e) => {
            opts.onpaginationchange({page: opts.page + 1});
        };

        this.changePage = (e) => {
            opts.onpaginationchange(e.item);
        };

        this.changeSize = (e) => {
            opts.onpaginationchange({
                size: +e.target.value
            });
        };

        this.calculate = () => {
            const totalPages = opts.size > 0 ? parseInt(opts.count / opts.size) + 1 : 1;
            let maxPages = opts.maxpages || 5;
            if(maxPages > totalPages) maxPages = totalPages;

            let first;
            let last;
            if(totalPages > maxPages) {
                const range = Math.floor(maxPages / 2);
                first = opts.page - range > 1 ? opts.page - range : 1;
                const left = (opts.page - first - range) * -1;
                last = opts.page + range < totalPages ? opts.page + range : totalPages;
                const right = (last - opts.page - range) * -1;
                if(right > 0 && left == 0) first -= right;
                if(left > 0 && right == 0) last += left;
            } else {
                first = 1;
                last = totalPages;
            }
            const pages = [];
            for(var i = first; i <= last; i++) pages.push(i);
            
            this.update({
                pages,
                totalPages
            });
        };
        
    </script>
</app-paginator>
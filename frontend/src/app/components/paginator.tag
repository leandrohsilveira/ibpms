<app-paginator> 
    
    <div class="app-paginator">
        <button disabled={opts.page < 3} class="button button-clear">{"<<"}</button>
        <button disabled={opts.page === 1} class="button button-clear">{"<"}</button>
        <button class={parent.getButtonClasses(page)} each={page in pages} onclick={this.parent.changePage}>{page}</button>
        <button disabled={opts.page >= totalPages} class="button button-clear">{">"}</button>
        <button disabled={opts.page >= (totalPages - 1)} class="button button-clear">{">>"}</button>
    </div>

    <style>
        :scope .button {
            padding: 0 15px;
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

        this.changePage = (e) => {
            opts.onchange(e.item);
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
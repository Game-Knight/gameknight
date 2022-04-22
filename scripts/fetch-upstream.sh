# If you're working on a fork, this will allow you to pull changes
# which have been made on the main repo

set -e
git stash
git fetch upstream
git merge upstream/master
git push
git stash pop
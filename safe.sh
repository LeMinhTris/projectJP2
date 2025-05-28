#!/bin/bash

# Add tất cả file
git add .

# Bỏ theo dõi các file rác nếu lỡ bị add
git rm --cached -r .idea 2>/dev/null
git rm --cached -r target 2>/dev/null
git rm --cached .DS_Store 2>/dev/null
git rm --cached *.iml 2>/dev/null
git rm --cached -r .vscode 2>/dev/null
git rm --cached *.properties 2>/dev/null
echo "✅ Safe add done. IDE and build files removed from staging."

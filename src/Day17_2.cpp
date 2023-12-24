#include <bits/stdc++.h>

using namespace std;

int main() {
    vector<string> mat;
    string aux;
    while (cin >> aux) {
        mat.push_back(aux);
    }
    int n = mat.size();
    int m = mat[0].size();
    int dr[] = {-1, 0, 1, 0};
    int dc[] = {0, 1, 0, -1};
    // {dist, { {row, col}, dir }}
    set<pair<int, pair<pair<int, int>, int>>> s;
    vector<vector<vector<int>>> dist(n, vector<vector<int>> (m, vector<int> (4, 1e9)));
    s.insert({0, {{0, 0}, 0}});
    dist[0][0][0] = 0;
    while (!s.empty()) {
        auto it = s.begin();
        int curDist = it->first;
        int row = it->second.first.first;
        int col = it->second.first.second;
        int dir = it->second.second;
        s.erase(it);
        if (curDist > dist[row][col][dir]) continue;
        for (int k = 0; k < 4; k++) {
            if ((row != 0 || col != 0) && (dir+2)%4 == k) continue;
            if (k == dir) continue;
            int newRow = row;
            int newCol = col;
            int newDist = curDist;
            for (int ite = 1; ite <= 10; ite++) {
                newRow += dr[k];
                newCol += dc[k];
                if (newRow < 0 || newRow >= n || newCol < 0 || newCol >= m) break;
                newDist += mat[newRow][newCol] - '0';
                if (ite < 4) continue;
                if (newDist < dist[newRow][newCol][k]) {
                    s.erase({dist[newRow][newCol][k], {{newRow, newCol}, k}});
                    dist[newRow][newCol][k] = newDist;
                    s.insert({newDist, {{newRow, newCol}, k}});
                }
            }
        }
    }
    int ans = 1e9;
    for (int i = 0; i < 4; i++) {
        ans = min(ans, dist[n-1][m-1][i]);
    }
    cout << ans << endl;
    return 0;
}
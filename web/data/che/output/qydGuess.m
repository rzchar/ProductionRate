clear;
close all

costCol = 2:10;
effortCol = 12:15;

pureNumWholeWeek5 = xlsread('analyzeV5.xls');
pureNumWholeWeek5(end+1,:) = 0;
normalizedPureNumberWholeWeek5 = mapminmax(pureNumWholeWeek5',0,1)';
sumWeeklyEffort5 = sum(normalizedPureNumberWholeWeek5(:,effortCol),2);
sumWeeklyCost5 = sum(normalizedPureNumberWholeWeek5(:,costCol),2);
%productivity5 = sumWeeklyEffort5./sumWeeklyCost5;
authors5 = pureNumWholeWeek5(:,16);
productivity5 = sumWeeklyCost5./authors5;
traditionalProductivity5 = normalizedPureNumberWholeWeek5(:,12)./authors5;
%pd5 = productivity5.*traditionalProductivity5;

disp('new productivity')
var(productivity5(1:end-1))

disp('old productivity')
var(traditionalProductivity5(1:end-1))

figure('name','v5 normal and traditional')
clf;
hold on;
plot(productivity5,'-o')
%plot(normalizedPureNumberWholeWeek5(:,12),'-x')
plot(traditionalProductivity5,'-x')
%plot(pd5,'-s')
%plot(mapminmax(productivity5 -traditionalProductivity5,0,1)./traditionalProductivity5,'-v')
hold off;

figure('name','output')
clf;
hold on;
plot(mapminmax(productivity5 -traditionalProductivity5,0,1)./traditionalProductivity5,'-x')
xlabel('time(week)')
ylabel('Non-trad/Trad')
hold off;